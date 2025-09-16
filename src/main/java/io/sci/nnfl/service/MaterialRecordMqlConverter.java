package io.sci.nnfl.service;

import com.openai.client.OpenAIClient;
import com.openai.models.ResponseFormatJsonObject;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseOutputItem;
import com.openai.models.responses.ResponseOutputMessage;
import com.openai.models.responses.ResponseOutputText;
import com.openai.models.responses.ResponseTextConfig;
import io.sci.nnfl.config.OpenAiProperties;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility component that relies on OpenAI to translate natural language prompts into
 * MongoDB query objects targeting {@code MaterialRecord} documents.
 */
@Component
public class MaterialRecordMqlConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialRecordMqlConverter.class);
    private static final Pattern CODE_FENCE_PATTERN = Pattern.compile("```(?:json)?\\s*(.*?)```", Pattern.DOTALL);

    private static final String MATERIAL_RECORD_GUIDE = """
You are an assistant that translates natural-language search requirements into MongoDB query filter documents
for the `materials` collection that stores `MaterialRecord` documents.
Each MaterialRecord document contains these relevant top-level fields:
- generalInfo[] sub-documents with keys such as dataRecordDate, countryOfOrigin, producer, supplier, analyticalLab, batchId and notes
- geology[] sub-documents describing depositType, hostRock, country, region, latitude and longitude
- mineralogy[], uranium[], uraniumIsotopes[], stableIsotopes[] and traceElements[] which contain measurement objects with name, value, unit and stage information
- chemicalForms[], physicals[], morphologies[], uraniumDecaySeriesRadionuclides[], processInformation[], elemental[], containers[], serialNumbers[], plutoniumIsotopes[], irradiationHistories[], isotopeActivities[], sourceDescriptions[] and sourceActivityInfo[] which are arrays of contextual sub-documents that also include a stage field
- creationDateTime (date) showing when the record was created
When a field is an array of sub-documents use `$elemMatch` to constrain nested properties.
Apply case-insensitive matching for free-form text by using a `$regex` with `$options: "i"`.
Dates must use extended JSON, for example `{ "$gte": { "$date": "2020-01-01T00:00:00Z" } }`.
Combine independent requirements with `$and` or `$or` arrays. Use comparison operators such as `$gte` and `$lte` when the request mentions ranges.
If the request cannot be represented with the available fields respond with an empty filter `{}`.
Example (do not repeat this in your response):
Request: samples from Canada created after 2015 with uranium 235 ratio above 0.7
Filter:
{
  "$and": [
    { "generalInfo": { "$elemMatch": { "countryOfOrigin": { "$regex": "^Canada$", "$options": "i" } } } },
    { "creationDateTime": { "$gte": { "$date": "2015-01-01T00:00:00Z" } } },
    { "uraniumIsotopes": { "$elemMatch": { "name": { "$regex": "235", "$options": "i" }, "value": { "$gt": 0.7 } } } }
  ]
}
""";

    private static final String PROMPT_TEMPLATE = """
%s

Produce a single MongoDB filter expressed as Extended JSON for MaterialRecord documents in the `materials` collection.
Respond with only the JSON object and no additional commentary or code fences.
User request:
%s
""";

    private final OpenAIClient openAiClient;
    private final OpenAiProperties properties;
    private final ResponseTextConfig jsonResponseConfig;

    public MaterialRecordMqlConverter(@Nullable OpenAIClient openAiClient, OpenAiProperties properties) {
        this.openAiClient = openAiClient;
        this.properties = properties;
        this.jsonResponseConfig = ResponseTextConfig.builder()
                .format(ResponseFormatJsonObject.builder().build())
                .build();
    }

    public boolean isConfigured() {
        return openAiClient != null && properties.hasApiKey();
    }

    public Optional<Query> toQuery(Optional<String> query) {
        return query.flatMap(this::parseDocument).map(BasicQuery::new);
    }

    public Optional<String> toJsonFilter(String naturalLanguageQuery) {
        if (!StringUtils.hasText(naturalLanguageQuery)) {
            return Optional.empty();
        }
        if (!isConfigured()) {
            LOGGER.warn("Skipping natural language search because the OpenAI client is not configured.");
            return Optional.empty();
        }

        String prompt = buildPrompt(naturalLanguageQuery.trim());

        try {
            ResponseCreateParams.Builder paramsBuilder = ResponseCreateParams.builder()
                    .model(properties.getModel())
                    .input(prompt)
                    .temperature(0d)
                    .text(jsonResponseConfig);

            int maxTokens = properties.getMaxOutputTokens();
            if (maxTokens > 0) {
                paramsBuilder.maxOutputTokens((long) maxTokens);
            }

            Response response = openAiClient.responses().create(paramsBuilder.build());
            return extractJson(response);
        } catch (Exception ex) {
            LOGGER.warn("Failed to translate natural language query into a MongoDB filter", ex);
            return Optional.empty();
        }
    }

    private Optional<String> extractJson(Response response) {
        if (response == null) {
            return Optional.empty();
        }

        List<ResponseOutputItem> outputItems = response.output();
        if (outputItems == null || outputItems.isEmpty()) {
            return Optional.empty();
        }

        String raw = outputItems.stream()
                .map(ResponseOutputItem::message)
                .flatMap(Optional::stream)
                .map(ResponseOutputMessage::content)
                .flatMap(List::stream)
                .map(ResponseOutputMessage.Content::outputText)
                .flatMap(Optional::stream)
                .map(ResponseOutputText::text)
                .collect(Collectors.joining("\n"))
                .trim();

        if (!StringUtils.hasText(raw)) {
            return Optional.empty();
        }

        String sanitized = sanitize(raw);
        return StringUtils.hasText(sanitized) ? Optional.of(sanitized) : Optional.empty();
    }

    private Optional<Document> parseDocument(String json) {
        try {
            return Optional.of(Document.parse(json));
        } catch (RuntimeException parseError) {
            LOGGER.warn("Generated JSON filter could not be parsed: {}", json, parseError);
            return Optional.empty();
        }
    }

    private String sanitize(String raw) {
        String candidate = raw.trim();

        Matcher codeFence = CODE_FENCE_PATTERN.matcher(candidate);
        if (codeFence.find()) {
            candidate = codeFence.group(1).trim();
        }

        candidate = candidate.replaceFirst("(?i)^(mongodb\\s+filter|filter|query)\\s*:\\s*", "");

        int start = candidate.indexOf('{');
        int end = candidate.lastIndexOf('}');
        if (start >= 0 && end > start) {
            candidate = candidate.substring(start, end + 1);
        }

        candidate = candidate.trim();
        if (candidate.endsWith(";")) {
            candidate = candidate.substring(0, candidate.length() - 1).trim();
        }
        return candidate;
    }

    private String buildPrompt(String query) {
        return String.format(Locale.ROOT, PROMPT_TEMPLATE, MATERIAL_RECORD_GUIDE, query);
    }
}

package io.sci.nnfl.config;

import com.openai.models.ChatModel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * Configuration properties for the OpenAI integration used by natural language search.
 */
@ConfigurationProperties(prefix = "openai")
public class OpenAiProperties {

    /**
     * API key used to authenticate requests against the OpenAI API.
     */
    private String apiKey;

    /**
     * Model identifier to use for translating natural language prompts into MQL.
     */
    private String model = ChatModel.GPT_4_1_MINI.asString();

    /**
     * Soft limit for the amount of tokens returned by the model when generating MQL.
     */
    private int maxOutputTokens = 512;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if (StringUtils.hasText(model)) {
            this.model = model;
        }
    }

    public int getMaxOutputTokens() {
        return maxOutputTokens;
    }

    public void setMaxOutputTokens(int maxOutputTokens) {
        if (maxOutputTokens > 0) {
            this.maxOutputTokens = maxOutputTokens;
        }
    }

    public boolean hasApiKey() {
        return StringUtils.hasText(apiKey);
    }
}

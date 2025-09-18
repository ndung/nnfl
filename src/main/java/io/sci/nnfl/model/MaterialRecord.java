package io.sci.nnfl.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "materials")
public class MaterialRecord {
    @Id
    private String id;
    private String name;
    private List<GeneralInfo> generalInfo;
    //use in stage 1
    private List<Geology> geology;
    //use in stage 1
    private List<Mineralogy> mineralogy;
    //use in stage 1, 2
    private List<Element> uranium;
    //use in stage 1, 2, 3, 4, 5, 6, 7
    private List<IsotopeRatio> uraniumIsotopes;
    //use in stage 1, 2
    private List<IsotopeRatio> stableIsotopes;
    //use in stage 1, 2, 3, 4, 5, 6, 7
    private List<Element> traceElements;
    //use in stage 2, 3, 4, 5, 6, 7, 9, 10
    private List<ChemicalForm> chemicalForms;
    //use in stage 2, 3, 4, 5, 6, 7, 8
    private List<Physical> physicals;
    //use in stage 2, 3, 4
    private List<Morphology> morphologies;
    //use in stage 2
    private List<UraniumDecaySeriesRadionuclide> uraniumDecaySeriesRadionuclides;
    //use in stage 2, 3, 4, 5, 7, 8
    private List<ProcessInformation> processInformation;
    //use in stage 3, 4, 5, 6, 8, 9, 10
    private List<Element> elemental;
    //use in stage 3, 8
    private List<Container> containers;
    //use in stage 4, 5, 6, 8
    private List<SerialNumber> serialNumbers;
    //use in stage 4, 5, 6, 7
    private List<IsotopeRatio> plutoniumIsotopes;
    //use in stage 6
    private List<IrradiationHistory> irradiationHistories;
    //use in stage 8, 9, 10
    private List<IsotopeActivity> isotopeActivities;
    //use in stage 9, 10
    private List<SourceDescription> sourceDescriptions;
    //use in stage 9, 10
    private List<SourceActivityInfo> sourceActivityInfo;
    private String notes;
    private User creator;
    private Date creationDateTime;

    @Transient
    public String getData(){
        Map<Stage,Map<String,List<Property>>> map = new LinkedTreeMap<>();
        extract(map, "generalInfo",generalInfo);
        extract(map, "geology",geology);
        extract(map, "mineralogy",mineralogy);
        extract(map, "uranium",uranium);
        extract(map, "uraniumIsotopes",uraniumIsotopes);
        extract(map, "stableIsotopes",stableIsotopes);
        extract(map, "traceElements",traceElements);
        extract(map, "chemicalForms",chemicalForms);
        extract(map, "physicals",physicals);
        extract(map, "morphologies",morphologies);
        extract(map, "uraniumDecaySeriesRadionuclides",uraniumDecaySeriesRadionuclides);
        extract(map, "processInformation",processInformation);
        extract(map, "elemental",elemental);
        extract(map, "containers",containers);
        extract(map, "serialNumbers",serialNumbers);
        extract(map, "plutoniumIsotopes",plutoniumIsotopes);
        extract(map, "irradiationHistories",irradiationHistories);
        extract(map, "isotopeActivities",isotopeActivities);
        extract(map, "sourceDescriptions",sourceDescriptions);
        extract(map, "sourceActivityInfo",sourceActivityInfo);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
                .excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(map);

    }

    private <T extends Property> void extract(Map<Stage,Map<String,List<Property>>> map,
                                              String name, List<T> list){
        if (list == null) return;
        for (Property property : list){
            if (!map.containsKey(property.getStage())){
                map.put(property.getStage(), new LinkedTreeMap<>());
            }
            Map<String,List<Property>> map1 = map.get(property.getStage());
            if (!map1.containsKey(name)){
                map1.put(name, new ArrayList<>());
            }
            map1.get(name).add(property);
            map.put(property.getStage(), map1);
        }
    }
}

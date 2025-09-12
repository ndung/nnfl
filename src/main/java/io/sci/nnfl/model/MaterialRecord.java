package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "materials")
public class MaterialRecord {

    @Id
    private String id;
    private String state;
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
    private LocalDateTime creationDateTime;
}

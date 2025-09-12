package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Physical {
    private Stage stage;
    private Measurement density;
    private String stateOfMatter;           // solid/liquid/gas
    private String mechanicalProperties;    // e.g., hardness, strength
    private String description;             // pellet/rod/plate description
    private Dimension dimension;
    private String claddingInfo;
    private String coatingInfo;
    private String assemblyStructure;
    private String surfaceOxideThickness;
    private Measurement mass;
    private String notes;
}

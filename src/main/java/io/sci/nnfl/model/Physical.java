package io.sci.nnfl.model;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Physical extends Property {
    private BigDecimal densityValue;
    private String densityUnit;
    private String stateOfMatter;
    private String mechanicalProperties;
    private String description;
    private String dimensions;
    private String claddingInfo;
    private String coatingInfo;
    private String assemblyStructure;
    private String surfaceOxideThickness;
    private BigDecimal massValue;
    private BigDecimal massUnit;
    private String serialNumbers;
    private String notes;
}

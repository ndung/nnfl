package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Physical extends Property {
    @Expose
    private BigDecimal densityValue;
    @Expose
    private String densityUnit;
    @Expose
    private String stateOfMatter;
    @Expose
    private String mechanicalProperties;
    @Expose
    private String description;
    @Expose
    private String dimensions;
    @Expose
    private String claddingInfo;
    @Expose
    private String coatingInfo;
    @Expose
    private String assemblyStructure;
    @Expose
    private String surfaceOxideThickness;
    @Expose
    private BigDecimal massValue;
    @Expose
    private BigDecimal massUnit;
    @Expose
    private String serialNumbers;
    @Expose
    private String notes;
}

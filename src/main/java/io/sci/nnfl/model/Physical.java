package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Physical {
    @Id
    private String id;
    private Stage stage;
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

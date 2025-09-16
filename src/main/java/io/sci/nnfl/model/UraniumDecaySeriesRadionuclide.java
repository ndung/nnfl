package io.sci.nnfl.model;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UraniumDecaySeriesRadionuclide extends Property {
    private String isotopeName;          // 230Th, 231Pa, 226Ra, ...
    private BigDecimal activityBq;
    private BigDecimal activityUncertaintyBq;
    private String notes;
}
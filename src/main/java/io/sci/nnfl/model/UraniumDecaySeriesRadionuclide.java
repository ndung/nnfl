package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UraniumDecaySeriesRadionuclide {
    private Stage stage;
    private String isotopeName;          // 230Th, 231Pa, 226Ra, ...
    private BigDecimal activityBq;
    private BigDecimal activityUncertaintyBq;
    private String notes;
}
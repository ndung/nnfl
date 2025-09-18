package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UraniumDecaySeriesRadionuclide extends Property {
    @Expose private String isotopeName;
    @Expose private BigDecimal activityBq;
    @Expose private BigDecimal activityUncertaintyBq;
    @Expose private String notes;
}
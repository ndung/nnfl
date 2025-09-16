package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsotopeRatio extends Property {
    @Expose
    private String name;
    @Expose
    private BigDecimal value;
    @Expose
    private BigDecimal uncertainty;
    @Expose
    private String unit;
    @Expose
    private String notes;
}

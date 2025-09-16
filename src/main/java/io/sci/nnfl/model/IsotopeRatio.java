package io.sci.nnfl.model;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsotopeRatio extends Property {
    private String name;
    private BigDecimal value;
    private BigDecimal uncertainty;
    private String unit;            // per mil (‰), ratio, epsilon, etc.
    private String notes;
}

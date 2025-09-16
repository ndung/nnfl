package io.sci.nnfl.model;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Element extends Property {
    private String element;
    private BigDecimal concentration;
    private String unit;
    private BigDecimal uncertainty;
    private String burnablePoison;
    private String notes;
}

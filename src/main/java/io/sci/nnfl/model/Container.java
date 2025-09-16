package io.sci.nnfl.model;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Container extends Property{
    private String type;     // e.g., UF6 cylinder type
    private BigDecimal volumeValue;        // SI if used
    private String volumeUnit;
    private String dimensions;
    private String notes;
}

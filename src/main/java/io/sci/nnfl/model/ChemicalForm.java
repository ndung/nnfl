package io.sci.nnfl.model;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChemicalForm extends Property{
    private String compoundName;          // U3O8, UF6, (NH4)2U2O7, etc.
    private BigDecimal stoichiometryDeviation;
    private String notes;
}

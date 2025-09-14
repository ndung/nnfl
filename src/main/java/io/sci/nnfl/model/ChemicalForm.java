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
public class ChemicalForm {
    private String id;
    private Stage stage;
    private String compoundName;          // U3O8, UF6, (NH4)2U2O7, etc.
    private BigDecimal stoichiometryDeviation;
    private String notes;
}

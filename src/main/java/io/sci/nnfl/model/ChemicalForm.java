package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChemicalForm extends Property{
    @Expose private String compoundName;
    @Expose private BigDecimal stoichiometryDeviation;
    @Expose private String notes;
}

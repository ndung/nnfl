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
public class Element {

    private Stage stage;
    private String element;
    private BigDecimal concentration;
    private String unit;
    private BigDecimal uncertainty;
    private String burnablePoison;
    private String notes;
}

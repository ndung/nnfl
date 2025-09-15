package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Element {
    @Id
    private String id;
    private Stage stage;
    private String element;
    private BigDecimal concentration;
    private String unit;
    private BigDecimal uncertainty;
    private String burnablePoison;
    private String notes;
}

package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Element extends Property {
    @Expose
    private String element;
    @Expose
    private BigDecimal concentration;
    @Expose
    private String unit;
    @Expose
    private BigDecimal uncertainty;
    @Expose
    private String burnablePoison;
    @Expose
    private String notes;
}

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
public class IsotopeRatio {
    @Id
    private String id;
    private Stage stage;
    private String name;
    private BigDecimal value;
    private BigDecimal uncertainty;
    private String unit;            // per mil (‰), ratio, epsilon, etc.
    private String notes;
}

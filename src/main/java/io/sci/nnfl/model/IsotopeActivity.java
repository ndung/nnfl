package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsotopeActivity {
    @Id
    private String id;
    private Stage stage;
    private String isotopeName;             // major or minor
    private BigDecimal activityBq;
    private BigDecimal activityUncertaintyBq;
    private LocalDate referenceDate;
    private Boolean major;                  // true = major, false = minor
    private String notes;
}

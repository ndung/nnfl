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
public class SourceActivityInfo {
    @Id
    private String id;
    private Stage stage;
    private BigDecimal activityBq;
    private LocalDate referenceDate;
    private BigDecimal neutronIntensityPerSec; // for neutron sources
    private String notes;
}

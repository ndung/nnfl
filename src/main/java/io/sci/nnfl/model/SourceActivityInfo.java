package io.sci.nnfl.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SourceActivityInfo extends Property {
    private BigDecimal activityBq;
    private Date referenceDate;
    private BigDecimal neutronIntensityPerSec; // for neutron sources
    private String notes;
}

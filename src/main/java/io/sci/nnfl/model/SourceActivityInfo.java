package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SourceActivityInfo extends Property {
    @Expose
    private BigDecimal activityBq;
    @Expose
    private Date referenceDate;
    @Expose
    private BigDecimal neutronIntensityPerSec; // for neutron sources
    @Expose
    private String notes;
}

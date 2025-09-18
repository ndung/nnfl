package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IrradiationHistory extends Property {
    @Expose private String reactorType;
    @Expose private String burnUp;
    @Expose private String assemblyPowerHistory;
    @Expose private String operatingRecordsRef;
    @Expose private Date loadDate;
    @Expose private Date dischargeDate;
    @Expose private String radiationLevel;
    @Expose private String notes;
}

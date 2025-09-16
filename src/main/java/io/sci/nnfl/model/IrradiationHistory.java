package io.sci.nnfl.model;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IrradiationHistory extends Property {
    private String reactorType;
    private String burnUp;                 // include actinides/fission products notes
    private String assemblyPowerHistory;
    private String operatingRecordsRef;
    private Date loadDate;
    private Date dischargeDate;
    private String radiationLevel;         // qualitative or numeric+units
    private String notes;
}

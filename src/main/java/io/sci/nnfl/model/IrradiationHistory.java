package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IrradiationHistory {
    private Stage stage;
    private String reactorType;
    private String burnUp;                 // include actinides/fission products notes
    private String assemblyPowerHistory;
    private String operatingRecordsRef;
    private LocalDate loadDate;
    private LocalDate dischargeDate;
    private String radiationLevel;         // qualitative or numeric+units
    private String notes;
}

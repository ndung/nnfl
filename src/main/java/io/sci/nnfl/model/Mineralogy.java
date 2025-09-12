package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mineralogy {
    private Stage stage;
    private String mineralsPresent;      // free text or CSV
    private String mineralChemistry;     // composition notes
    private String volumePercentages;    // "Quartz 40%, Feldspar 20%..."
    private String notes;
}

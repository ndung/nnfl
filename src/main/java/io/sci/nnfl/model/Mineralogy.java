package io.sci.nnfl.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mineralogy extends Property {
    private String mineralsPresent;      // free text or CSV
    private String mineralChemistry;     // composition notes
    private String volumePercentages;    // "Quartz 40%, Feldspar 20%..."
    private String notes;
}

package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mineralogy {
    @Id
    private String id;
    private Stage stage;
    private String mineralsPresent;      // free text or CSV
    private String mineralChemistry;     // composition notes
    private String volumePercentages;    // "Quartz 40%, Feldspar 20%..."
    private String notes;
}

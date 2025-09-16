package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mineralogy extends Property {
    @Expose
    private String mineralsPresent;
    @Expose
    private String mineralChemistry;
    @Expose
    private String volumePercentages;
    @Expose
    private String notes;
}

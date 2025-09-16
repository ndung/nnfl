package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Geology extends Property {
    @Expose
    private String mineLocation;
    @Expose
    private String geologicalFormation;
    @Expose
    private String depositTypes;
    @Expose
    private String miningTechnique;
    @Expose
    private String colour;
    @Expose
    private String notes;
}

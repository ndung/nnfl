package io.sci.nnfl.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Geology extends Property {
    private String mineLocation;
    private String geologicalFormation;
    private String depositTypes;         // e.g., vein, roll-front
    private String miningTechnique;
    private String colour;
    private String notes;
}

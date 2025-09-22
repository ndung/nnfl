package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Morphology extends Property {
    @Expose private String latticeStructure;
    @Expose private String aspectRatio;
    @Expose private String porosity;
    @Expose private String colour;
    @Expose private String particleSizeAndDistribution;
    @Expose private String shape;
    @Expose private String surfaceFeatures;
    @Expose private String plutoniumHomogeneity;
    @Expose private String notes;
    @Expose private String imageFile;
}
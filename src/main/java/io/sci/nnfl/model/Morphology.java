package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Morphology {
    private Stage stage;
    private String latticeStructure;
    private String aspectRatio;
    private String porosity;
    private String colour;
    private String particleSizeAndDistribution;
    private String shape;
    private String surfaceFeatures;         // e.g., striations
    private String plutoniumHomogeneity;    // for MOX; optional
    private String notes;
}
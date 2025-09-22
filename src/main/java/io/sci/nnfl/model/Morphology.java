package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Morphology extends Property {
    @Expose private BigDecimal magnification;
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
    @Transient
    public String getFileUrl(){
        return "http://10.10.253.75/files/"+imageFile;
    }
}
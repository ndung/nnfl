package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;
import org.springframework.data.annotation.Transient;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SourceDescription extends Property {
    @Expose private String sourceType;
    @Expose private String quantity;
    @Expose private String description;
    @Expose private String dimensions;
    @Expose private String encapsulationOrCladding;
    @Expose private String serialNumber;
    @Expose private String shippingHistory;
    @Expose private String receivingHistory;
    @Expose private String radiographOrPhotograph;
    @Expose private String imageFile;
    @Transient
    public String getFileUrl(){
        return "http://10.10.253.75/files/"+imageFile;
    }
    @Expose private String notes;
}

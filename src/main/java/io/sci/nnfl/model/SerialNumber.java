package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SerialNumber extends Property {
    @Expose private String serialNumber;
    @Expose private String notes;
}

package io.sci.nnfl.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SerialNumber extends Property {
    private String serialNumber;
    private String notes;
}

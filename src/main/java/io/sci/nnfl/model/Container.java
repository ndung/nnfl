package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Container extends Property{
    @Expose private String type;
    @Expose private BigDecimal volumeValue;
    @Expose private String volumeUnit;
    @Expose private String dimensions;
    @Expose private String notes;
}

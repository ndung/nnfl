package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsotopeActivity extends Property {
    @Expose private String isotopeName;
    @Expose private BigDecimal activityBq;
    @Expose private BigDecimal activityUncertaintyBq;
    @Expose private Date referenceDate;
    @Expose private Boolean major;
    @Expose private String notes;
}

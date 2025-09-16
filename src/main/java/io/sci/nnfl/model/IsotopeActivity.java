package io.sci.nnfl.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsotopeActivity extends Property {
    private String isotopeName;             // major or minor
    private BigDecimal activityBq;
    private BigDecimal activityUncertaintyBq;
    private Date referenceDate;
    private Boolean major;                  // true = major, false = minor
    private String notes;
}

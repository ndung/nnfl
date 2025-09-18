package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessInformation extends Property {
    @Expose private String processTypeOrDescription;
    @Expose private String locationOfProcessingSite;
    @Expose private Date startDate;
    @Expose private Date endDate;
    @Expose private String notes;
}
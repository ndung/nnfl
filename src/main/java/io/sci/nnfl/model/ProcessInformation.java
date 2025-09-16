package io.sci.nnfl.model;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessInformation extends Property {
    private String processTypeOrDescription;   // milling, fluorination, fab, repro...
    private String locationOfProcessingSite;
    private Date startDate;
    private Date endDate;
    private String notes;
}
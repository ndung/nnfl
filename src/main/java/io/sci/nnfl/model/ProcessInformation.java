package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessInformation {
    private Stage stage;
    private String processTypeOrDescription;   // milling, fluorination, fab, repro...
    private String locationOfProcessingSite;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;
}
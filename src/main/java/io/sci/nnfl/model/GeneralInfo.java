package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralInfo {
    @Id
    private String id;
    private Stage stage;
    private LocalDate dataRecordDate;
    private String custodian;
    private String analyticalLab;
    private LocalDate analysisDate;
    private String countryOfOrigin;
    private String producer;
    private String supplier;
    private String batchId;
    private LocalDate batchProcessDate;
    private String shipperCarrier;
    private String receiverInfo;
    private LocalDate shippingDate;
    private LocalDate receivingDate;
    private String dataEvaluationInfo;     // quality/pedigree/completeness
    private String variationRangeNotes;    // technical spec/variation info
    private LocalDate informationAcquisitionDate;
    private Boolean usedArchivedInformation;
    private String notes;
}

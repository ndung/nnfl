package io.sci.nnfl.model;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralInfo extends Property {
    private Date dataRecordDate;
    private String custodian;
    private String analyticalLab;
    private Date analysisDate;
    private String countryOfOrigin;
    private String producer;
    private String supplier;
    private String batchId;
    private Date batchProcessDate;
    private String shipperCarrier;
    private String receiverInfo;
    private Date shippingDate;
    private Date receivingDate;
    private String dataEvaluationInfo;     // quality/pedigree/completeness
    private String variationRangeNotes;    // technical spec/variation info
    private Date informationAcquisitionDate;
    private Boolean usedArchivedInformation;
    private String notes;
}

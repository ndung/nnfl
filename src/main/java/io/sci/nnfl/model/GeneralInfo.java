package io.sci.nnfl.model;

import com.google.gson.annotations.Expose;
import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralInfo extends Property {
    @Expose private Date dataRecordDate;
    @Expose private String custodian;
    @Expose private String analyticalLab;
    @Expose private Date analysisDate;
    @Expose private String countryOfOrigin;
    @Expose private String producer;
    @Expose private String supplier;
    @Expose private String batchId;
    @Expose private Date batchProcessDate;
    @Expose private String shipperCarrier;
    @Expose private String receiverInfo;
    @Expose private Date shippingDate;
    @Expose private Date receivingDate;
    @Expose private String dataEvaluationInfo;
    @Expose private String variationRangeNotes;
    @Expose private Date informationAcquisitionDate;
    @Expose private Boolean usedArchivedInformation;
    @Expose private String notes;
    @Expose private String imageFile;
}

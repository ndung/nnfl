package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SourceDescription {
    private Stage stage;                 // SEALED_SOURCE / UNSEALED_SOURCE
    private String sourceType;           // emission type / intended use
    private String quantity;             // textual or calculated
    private String description;          // description and dimensions
    private Dimension dimension;
    private String encapsulationOrCladding;
    private String serialNumber;
    private String shippingReceivingHistory;
    private String radiographOrPhotoRef; // link/ID
    private String notes;
}

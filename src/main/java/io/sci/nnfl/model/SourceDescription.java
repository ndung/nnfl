package io.sci.nnfl.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SourceDescription extends Property {
    private String sourceType;           // emission type / intended use
    private String quantity;             // textual or calculated
    private String description;          // description and dimensions
    private String dimensions;
    private String encapsulationOrCladding;
    private String serialNumber;
    private String shippingHistory;
    private String receivingHistory;
    private String radiographOrPhotograph; // link/ID
    private String notes;
}

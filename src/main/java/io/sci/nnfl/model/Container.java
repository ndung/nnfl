package io.sci.nnfl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Container {
    private Stage stage;
    private String type;     // e.g., UF6 cylinder type
    private BigDecimal volume;        // SI if used
    private Dimension dimension;
    private String serialNumber;
    private String notes;
}

package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Container {
    @Id
    private String id;
    private Stage stage;
    private String type;     // e.g., UF6 cylinder type
    private BigDecimal volumeValue;        // SI if used
    private String volumeUnit;
    private String dimensions;
    private String notes;
}

package io.sci.nnfl.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Geology {
    @Id
    private String id;
    private Stage stage;
    private String mineLocation;
    private String geologicalFormation;
    private String depositTypes;         // e.g., vein, roll-front
    private String miningTechnique;
    private String colour;
    private String notes;
}

package io.sci.nnfl.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public class Property {
    @Id
    @Getter @Setter
    protected String id;
    @Getter @Setter
    protected Stage stage;
}

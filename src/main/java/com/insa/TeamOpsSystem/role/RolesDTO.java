package com.insa.TeamOpsSystem.role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class RolesDTO extends RepresentationModel<RolesDTO> {
    private final Long id;
    private final String name;


    public RolesDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

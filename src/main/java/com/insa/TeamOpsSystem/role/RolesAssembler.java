package com.insa.TeamOpsSystem.role;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class RolesAssembler implements RepresentationModelAssembler<Roles, RolesDTO> {

    @Override
    public RolesDTO toModel(Roles roles) {
        return new RolesDTO(
                roles.getId(),
                roles.getName()
        );
    }
}

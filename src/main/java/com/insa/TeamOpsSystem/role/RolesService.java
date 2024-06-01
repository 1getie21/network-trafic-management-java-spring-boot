package com.insa.TeamOpsSystem.role;

import com.insa.TeamOpsSystem.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RolesService {
    Page<Roles> findAll(Pageable pageable);

    RolesDTO addRoles(Roles roles);

    Roles findRolesById(Long id);
    RolesDTO deleteById(Long id) throws ResourceNotFoundException;

    Roles updateRoles(Roles roles, long roleId);
}

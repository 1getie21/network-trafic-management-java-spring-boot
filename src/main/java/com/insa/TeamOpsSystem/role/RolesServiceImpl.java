package com.insa.TeamOpsSystem.role;

import com.insa.TeamOpsSystem.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.insa.TeamOpsSystem.jwt.until.Util.getNullPropertyNames;

@Service
@AllArgsConstructor
public class RolesServiceImpl implements RolesService {

    RolesRepository rolesRepository;
    RolesAssembler rolesAssembler;

    public Page<Roles> findAll(Pageable pageable) {
       return   rolesRepository.findAll(pageable);
    }

    @Override
    public RolesDTO addRoles(Roles roles) {
        return rolesAssembler.toModel(
                rolesRepository.save(roles)
        );
    }

    @Override
    public Roles findRolesById(Long id) {
        try {
            return rolesRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException(
                            "Role with ID :" + id + " Not Found!")
            );
        }
        catch (Exception e){
            throw new ResourceNotFoundException(e.getMessage());
        }

    }

    @Override
    public RolesDTO deleteById(Long id) throws ResourceNotFoundException {
        Roles roles = rolesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Role with ID :" + id + " Not Found!")
        );
        rolesRepository.deleteById(roles.getId());
        return rolesAssembler.toModel(roles);
    }

    @Override
    public Roles updateRoles( Roles roles,long roleId)     {
        var ro = findRolesById(roleId);

        BeanUtils.copyProperties(roles, ro, getNullPropertyNames(roles));
        return rolesRepository.save(ro);
    }
}


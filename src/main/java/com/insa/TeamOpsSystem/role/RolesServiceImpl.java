package com.insa.TeamOpsSystem.role;
import com.insa.TeamOpsSystem.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


@Service
@AllArgsConstructor
public class RolesServiceImpl implements RolesService {

    RolesRepository rolesRepository;
    RolesAssembler rolesAssembler;
    PagedResourcesAssembler pagedResourcesAssembler;

    @Override
    public PagedModel findAll(int page, int size) {
        PageRequest pageRequest;
        pageRequest = PageRequest.of(page, size);
        Page<Roles> roles = rolesRepository.findAll(pageRequest);
        if (!CollectionUtils.isEmpty(roles.getContent()))
            return pagedResourcesAssembler.toModel(roles, rolesAssembler);

        return null;
    }

    @Override
    public RolesDTO addRoles(Roles roles) {
        return rolesAssembler.toModel(
                rolesRepository.save(roles)
        );
    }

    @Override
    public RolesDTO findRolesById(Long id) {
        Roles roles = rolesRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Role with ID :" + id + " Not Found!")
        );
        return rolesAssembler.toModel(roles);

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
}


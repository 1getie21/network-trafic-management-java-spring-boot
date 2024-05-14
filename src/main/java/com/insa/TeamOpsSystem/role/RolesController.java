package com.insa.TeamOpsSystem.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RolesController   {

    RolesService rolesService;

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public RolesDTO addRoles( @RequestBody Roles roles) {
         return rolesService.addRoles(roles);
    }

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping
    public Page<Roles> findAll(Pageable pageable) {
        return    rolesService.findAll(pageable);
    }
    @PutMapping("/{roleId}")
    public Roles updateRoles(@RequestBody Roles roles,@PathVariable long roleId) {
        return    rolesService.updateRoles(roles,roleId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Roles findRolesById(@PathVariable Long id) {
      return rolesService.findRolesById(id);
    }
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> deleteRole(@PathVariable Long id) {

        var isRemoved = rolesService.deleteById(id);

        if (isRemoved == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}

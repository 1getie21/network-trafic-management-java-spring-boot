package com.insa.TeamOpsSystem.user;

import com.insa.TeamOpsSystem.role.RolesService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

public interface UserApi {
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    UserDto createTeamMembers(@RequestBody @Valid UserDto userDto) throws IllegalAccessException;
     @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDto getTeamMembersById(@PathVariable("id") long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDto updateTeamMembers(@PathVariable("id") long expenseId, @RequestBody @Valid UserDto userDto, UsernamePasswordAuthenticationToken token ) throws IllegalAccessException;
    @DeleteMapping("/{id}")
    public default void deleteUsersById(@PathVariable("id") Long id) throws IllegalAccessException {
        RolesService userRepository = null;
        userRepository.deleteById(id);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PagedModel<UserDto>> getAllTeamMembers(@Parameter(description = "pagination object", schema = @Schema(implementation = Pageable.class))
                                                       @Valid Pageable pageable,
                                                       PagedResourcesAssembler assembler,
                                                       UriComponentsBuilder uriBuilder,
                                                       final HttpServletResponse response);
}

package com.insa.TeamOpsSystem.user;

import com.insa.TeamOpsSystem.exceptions.AlreadyExistException;
import com.insa.TeamOpsSystem.jwt.JwtResponse;
import com.insa.TeamOpsSystem.jwt.JwtUtils;
import com.insa.TeamOpsSystem.jwt.PaginatedResultsRetrievedEvent;
import com.insa.TeamOpsSystem.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://10.10.10.112:8088"})
public class UsersController implements UserApi {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    @Override
    public UserDto createTeamMembers(UserDto userDto) throws IllegalAccessException {
        return userMapper.toUsersDto(userService.createTeamMembers(userMapper.toUsers(userDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteUsersById(@PathVariable("id") Long id) throws IllegalAccessException {
        userRepository.deleteById(id);
    }

    @PostMapping("/sign-in")
    public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            JwtResponse jwtResponse = new JwtResponse();
            jwtResponse.setToken(jwt);
            jwtResponse.setId(userDetails.getId());
            jwtResponse.setUsername(userDetails.getUsername());
            jwtResponse.setFirstName(userDetails.getFirstName());
            jwtResponse.setLastName(userDetails.getLastName());
            jwtResponse.setEmail(userDetails.getEmail());
            jwtResponse.setRoles(roles);
            return jwtResponse;
        } catch (Exception exception) {
            throw new AlreadyExistException(exception.getMessage());
        }
    }

    @Override
    public UserDto getTeamMembersById(long id) {
        return userMapper.toUsersDto(userService.getTeamMembersById(id));
    }


    @Override
    public UserDto updateTeamMembers(long id, UserDto userDto, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return userMapper.toUsersDto(userService.updateTeamMembers(id, userMapper.toUsers(userDto), token));
    }


    @Override
    public ResponseEntity<PagedModel<UserDto>> getAllTeamMembers(Pageable pageable, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                UserDto.class, uriBuilder, response, pageable.getPageNumber(), userService.getAllTeamMembers(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<UserDto>>(assembler.toModel(userService.getAllTeamMembers(pageable).map(userMapper::toUsersDto)), HttpStatus.OK);
    }
}

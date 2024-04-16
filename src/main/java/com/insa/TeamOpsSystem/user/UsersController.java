package com.insa.TeamOpsSystem.user;

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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController implements UserApi {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public UserDto createStudents(UserDto userDto) throws IllegalAccessException {
        return userMapper.toUsersDto(userService.createStudents(userMapper.toUsers(userDto)));
    }


    @PostMapping("/sign-in")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

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
    }

    @Override
    public UserDto getStudentsById(long id) {
        return userMapper.toUsersDto(userService.getStudentsById(id));
    }



    @Override
    public UserDto updateStudents(long id, UserDto userDto, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return userMapper.toUsersDto(userService.updateStudents(id, userMapper.toUsers(userDto),token));
    }


    @Override
    public ResponseEntity<PagedModel<UserDto>> getAllStudents(Pageable pageable, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                UserDto.class, uriBuilder, response, pageable.getPageNumber(), userService.getAllStudents(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<UserDto>>(assembler.toModel(userService.getAllStudents(pageable).map(userMapper::toUsersDto)), HttpStatus.OK);
    }
}

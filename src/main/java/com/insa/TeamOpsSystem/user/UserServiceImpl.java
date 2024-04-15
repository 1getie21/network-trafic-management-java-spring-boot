package com.insa.TeamOpsSystem.user;


import com.insa.TeamOpsSystem.exceptions.AlreadyExistException;
import com.insa.TeamOpsSystem.exceptions.EntityNotFoundException;
import com.insa.TeamOpsSystem.role.Roles;
import com.insa.TeamOpsSystem.role.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.insa.TeamOpsSystem.until.Util.getNullPropertyNames;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RolesRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public SystemUsers createStudents(SystemUsers signupRequest) throws IllegalAccessException {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new AlreadyExistException("User name '" + signupRequest.getUsername() + "' is already exist");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new AlreadyExistException("Email '" + signupRequest.getEmail() + "' is already exist");
        }

        Set<Roles> strRoles = signupRequest.getRole();
        Set<Roles> roles = new HashSet<>();
            strRoles.forEach(role -> {
                        Roles adminRole =  roleRepository.findById(role.getId())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

            });
        signupRequest.setPassword(encoder.encode(signupRequest.getPassword()));
        signupRequest.setRole(roles);
     return    userRepository.save(signupRequest);
    }



    @Override
    public SystemUsers getStudentsById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(SystemUsers.class, "  Type with an id: " + id + " was not found!"));
    }


    @Override
    public Page<SystemUsers> getAllStudents(Pageable pageable ) {
        return userRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public SystemUsers updateStudents(long id, SystemUsers systemUsers, UsernamePasswordAuthenticationToken token ) throws IllegalAccessException {
        var et = getStudentsById(id);
        BeanUtils.copyProperties(systemUsers, et, getNullPropertyNames(systemUsers));
        return userRepository.save(et);
    }

    @Override
    public void deleteStudents(long id, JwtAuthenticationToken token) {
        userRepository.deleteById(id);
    }



}

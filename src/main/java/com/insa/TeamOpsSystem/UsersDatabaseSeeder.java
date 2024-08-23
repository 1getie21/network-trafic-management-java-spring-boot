package com.insa.TeamOpsSystem;

import com.insa.TeamOpsSystem.role.Roles;
import com.insa.TeamOpsSystem.role.RolesRepository;
import com.insa.TeamOpsSystem.user.SystemUsers;
import com.insa.TeamOpsSystem.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Log4j2
public class UsersDatabaseSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Set<Roles> setRoles = new HashSet<>();
        SystemUsers user = new SystemUsers();
        if (rolesRepository.findByName("ROLE_ADMIN").isEmpty()) {
            Roles roles = new Roles();
            roles.setName("ROLE_ADMIN");
            rolesRepository.save(roles);
        }
        if (rolesRepository.findByName("ROLE_USER").isEmpty()) {
            Roles roles = new Roles();
            roles.setName("ROLE_USER");
            rolesRepository.save(roles);
        }
        if (rolesRepository.findByName("ROLE_MEMBER").isEmpty()) {
            Roles roles = new Roles();
            roles.setName("ROLE_MEMBER");
            rolesRepository.save(roles);
        }
     if (userRepository.findByUsername("Admin").isEmpty()) {
            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setUsername("Admin");
            user.setEmail("admin@gmail.com");
            user.setPassword(passwordEncoder.encode("1234"));
            Roles adminRole =   rolesRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            setRoles.add(adminRole);
            user.setRole(setRoles);
            userRepository.save(user);
        }
    }
}

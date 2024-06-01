package com.insa.TeamOpsSystem.user;

import com.insa.TeamOpsSystem.role.Roles;
import com.insa.TeamOpsSystem.jwt.until.Auditable;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto extends Auditable {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Roles> role;
}

package com.insa.TeamOpsSystem.jwt;

import lombok.Data;
import java.util.List;
@Data
public class JwtResponse {
  private Long id;
  private String token;
  private String type = "Bearer";
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  private List<String> roles;
}

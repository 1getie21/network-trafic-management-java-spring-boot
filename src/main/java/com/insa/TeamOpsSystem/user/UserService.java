package com.insa.TeamOpsSystem.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;



@Service
public interface UserService {
    SystemUsers createTeamMembers(SystemUsers TeamMembers ) throws IllegalAccessException;

     SystemUsers getTeamMembersById(long id);

    Page<SystemUsers> getAllTeamMembers(Pageable pageable);
     SystemUsers updateTeamMembers(long id, SystemUsers systemUsers , UsernamePasswordAuthenticationToken token) throws IllegalAccessException;
    void deleteTeamMembers(long id, JwtAuthenticationToken token);

}







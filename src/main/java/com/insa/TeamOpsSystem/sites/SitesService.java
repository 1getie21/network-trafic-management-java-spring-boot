package com.insa.TeamOpsSystem.sites;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;



@Service
public interface SitesService {
    Sites createTraffics(Sites sites, UsernamePasswordAuthenticationToken token) throws IllegalAccessException;
    Sites getTrafficById(long id);

    Page<Sites> getAllTraffics(Pageable pageable );
    Sites updateTrafficById(long id, Sites sections, UsernamePasswordAuthenticationToken token) throws IllegalAccessException;
    void deleteTrafficById(long id,UsernamePasswordAuthenticationToken token);

}







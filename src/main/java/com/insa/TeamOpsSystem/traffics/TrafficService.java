package com.insa.TeamOpsSystem.traffics;


import com.insa.TeamOpsSystem.exceptions.EntityNotFoundException;
import com.insa.TeamOpsSystem.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.insa.TeamOpsSystem.jwt.until.Util.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class TrafficService {
    private final TrafficRepository trafficRepository;

    public Traffics createTraffics(Traffics traffics, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        traffics.setEightTime(Times.EIGHT);
        traffics.setFortiethTime(Times.FORTIETH);
        traffics.setEighteenTime(Times.EIGHTEEN);
        UserDetails userDetails = (UserDetails) token.getPrincipal();
        traffics.setCreatedBy(userDetails.getUsername());
        traffics.setUpdated_by(userDetails.getUsername());
        return trafficRepository.save(traffics);
    }

    public Traffics getTrafficById(long id) {
        return trafficRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Traffics.class, "  Type with an id: " + id + " was not found!"));
    }


    public Page<Traffics> getAllTraffics(Pageable pageable, UsernamePasswordAuthenticationToken token) {
        UserDetailsImpl userDetails = (UserDetailsImpl) token.getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return trafficRepository.findAllBySitesDeletedIsFalse(pageable);
        } else {
            return trafficRepository.findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(userDetails.getUsername(), pageable);
        }
    }


    public Traffics updateTrafficById(long id, Traffics traffics, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        var et = getTrafficById(id);

        BeanUtils.copyProperties(traffics, et, getNullPropertyNames(traffics));
        return trafficRepository.save(et);
    }


    public void deleteTrafficById(long id, UsernamePasswordAuthenticationToken token) {
        trafficRepository.deleteById(id);
    }


}

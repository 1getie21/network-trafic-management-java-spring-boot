package com.insa.TeamOpsSystem.ftraffic;


import com.insa.TeamOpsSystem.exceptions.EntityNotFoundException;
import com.insa.TeamOpsSystem.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.insa.TeamOpsSystem.until.Util.getNullPropertyNames;


@Service
@RequiredArgsConstructor
public class FTrafficService {
    private final FTrafficRepository fTrafficRepository;


    public Ftraffics createTraffics(Ftraffics fTraffics, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        UserDetails userDetails = (UserDetails) token.getPrincipal();
        fTraffics.setCreatedBy(userDetails.getUsername());
        fTraffics.setUpdated_by(userDetails.getUsername());

        return fTrafficRepository.save(fTraffics);
    }

    public Ftraffics getTrafficById(long id) {
        return fTrafficRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Ftraffics.class, "  Type with an id: " + id + " was not found!"));
    }


    public Page<Ftraffics> getAllTraffics(Pageable pageable, UsernamePasswordAuthenticationToken token) {
        UserDetailsImpl userDetails = (UserDetailsImpl) token.getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            System.out.println("x");
            return fTrafficRepository.findAllBySitesDeletedIsFalse(pageable);
        } else {
            System.out.println("y");
            return fTrafficRepository.findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(userDetails.getUsername(), pageable);
        }
    }
    public Page<Ftraffics> getAllTrafficsByTrafficTime(String trafficTime,Pageable pageable) {

            return fTrafficRepository.findAllBySitesDeletedIsFalseAndTrafficTimeName(trafficTime, pageable);

    }


    public Ftraffics updateTrafficById(long id, Ftraffics fTraffics, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        var et = getTrafficById(id);


        BeanUtils.copyProperties(fTraffics, et, getNullPropertyNames(fTraffics));


        return fTrafficRepository.save(et);
    }


    public void deleteTrafficById(long id, UsernamePasswordAuthenticationToken token) {
        fTrafficRepository.deleteById(id);
    }


}

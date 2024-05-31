package com.insa.TeamOpsSystem.failedTraffics;


import com.insa.TeamOpsSystem.exceptions.EntityNotFoundException;
import com.insa.TeamOpsSystem.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.insa.TeamOpsSystem.until.Util.getNullPropertyNames;


@Service
@RequiredArgsConstructor
public class FailedTrafficService {
    private final FailedTrafficRepository failedTrafficRepository;


    public FailedTraffics createTraffics(FailedTraffics failedTraffics, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        UserDetails userDetails = (UserDetails) token.getPrincipal();
        failedTraffics.setCreatedBy(userDetails.getUsername());
        failedTraffics.setUpdated_by(userDetails.getUsername());
        Duration duration = Duration.between(failedTraffics.getDisConnectedAt(), failedTraffics.getFixedAt());
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long remainingSeconds = duration.getSeconds() % 60;
        String durationString=days+" Days,"+hours+" hrs, "+minutes+" min, "+remainingSeconds+" scs";

        failedTraffics.setFailureLength(durationString);
        return failedTrafficRepository.save(failedTraffics);
    }

    public FailedTraffics getTrafficById(long id) {
        return failedTrafficRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(FailedTraffics.class, "  Type with an id: " + id + " was not found!"));
    }


    public Page<FailedTraffics> getAllTraffics(Pageable pageable,UsernamePasswordAuthenticationToken token) {
        UserDetailsImpl userDetails = (UserDetailsImpl) token.getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {

            return failedTrafficRepository.findAllBySitesDeletedIsFalse(pageable);
        } else {

            return failedTrafficRepository.findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(userDetails.getUsername(), pageable);
        }
    }

    public FailedTraffics updateTrafficById(long id, FailedTraffics failedTraffics, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        var et = getTrafficById(id);
        Duration duration = Duration.between(failedTraffics.getDisConnectedAt(), failedTraffics.getFixedAt());
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long remainingSeconds = duration.getSeconds() % 60;
        String durationString=days+" Days,"+hours+" hrs, "+minutes+" min, "+remainingSeconds+" scs";

        failedTraffics.setFailureLength(durationString);

        BeanUtils.copyProperties(failedTraffics, et, getNullPropertyNames(failedTraffics));


        return failedTrafficRepository.save(et);
    }


    public void deleteTrafficById(long id, UsernamePasswordAuthenticationToken token) {
        failedTrafficRepository.deleteById(id);
    }


}

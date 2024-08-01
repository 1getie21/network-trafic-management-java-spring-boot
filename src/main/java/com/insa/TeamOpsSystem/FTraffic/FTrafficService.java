package com.insa.TeamOpsSystem.FTraffic;

import com.insa.TeamOpsSystem.exceptions.AlreadyExistException;
import com.insa.TeamOpsSystem.exceptions.EntityNotFoundException;
import com.insa.TeamOpsSystem.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.insa.TeamOpsSystem.jwt.until.Util.getNullPropertyNames;


@Service
@RequiredArgsConstructor
public class FTrafficService {
    private final FTrafficRepository fTrafficRepository;

    public Ftraffics createTraffics(Ftraffics fTraffics, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        UserDetails userDetails = (UserDetails) token.getPrincipal();
        fTraffics.setCreatedBy(userDetails.getUsername());
        fTraffics.setUpdated_by(userDetails.getUsername());
        List<Ftraffics> traffics = fTrafficRepository.findAllByCreatedAtIsGreaterThanEqualAndTrafficTimeNameAndSitesIdAndSitesDeletedIsFalse(LocalDate.now().atStartOfDay(), fTraffics.getTrafficTimeName(), fTraffics.getSites().getId());
        if (traffics.isEmpty()) {
            return fTrafficRepository.save(fTraffics);
        } else throw new AlreadyExistException("Site already exist");
    }

    public Ftraffics getTrafficById(long id) {
        return fTrafficRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Ftraffics.class, "  Type with an id: " + id + " was not found!"));
    }

    public Page<Ftraffics> getAllTraffics(Pageable pageable, UsernamePasswordAuthenticationToken token) {
        UserDetailsImpl userDetails = (UserDetailsImpl) token.getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return fTrafficRepository.findAllBySitesDeletedIsFalse(pageable);
        } else {
            return fTrafficRepository.findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(userDetails.getUsername(), pageable);
        }
    }

    public Ftraffics updateTrafficById(long id, Ftraffics fTraffics, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        var et = getTrafficById(id);

        BeanUtils.copyProperties(fTraffics, et, getNullPropertyNames(fTraffics));
        return fTrafficRepository.save(et);
    }

    public void deleteTrafficById(long id, UsernamePasswordAuthenticationToken token) {
        fTrafficRepository.deleteById(id);
    }

//    public Page<Ftraffics> getAllTrafficsByTrafficTime(String trafficTime, Pageable pageable) {
//        return fTrafficRepository.findAllBySitesDeletedIsFalseAndTrafficTimeName(trafficTime, pageable);
//    }

    public Page<Ftraffics> getAllTrafficsByTrafficTime(String trafficTime, UsernamePasswordAuthenticationToken token, Pageable pageable) {
        UserDetails userDetails = (UserDetails) token.getPrincipal();
        String createdBy = userDetails.getUsername();

        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            // For ROLE_ADMIN, fetch all traffic records for the specified trafficTime
            return fTrafficRepository.findAllByTrafficTimeNameAndSitesDeletedIsFalse(trafficTime, pageable);
        } else {
            // For other users, fetch traffic records created by the current user for the specified trafficTime
            return fTrafficRepository.findAllByTrafficTimeNameAndCreatedByAndSitesDeletedIsFalse(trafficTime, createdBy, pageable);
        }
    }

    public Page<Ftraffics> findAllByCreatedAtBetween(LocalDate from, LocalDate to, UsernamePasswordAuthenticationToken token, Pageable pageable) {

        UserDetails userDetails = (UserDetails) token.getPrincipal();
        String createdBy = userDetails.getUsername();
        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return fTrafficRepository.findAllByCreatedAtBetweenAndSitesDeletedIsFalse(from.atStartOfDay(),
                    to.plusDays(1).atStartOfDay(), pageable);
        } else {
            return fTrafficRepository.findAllByCreatedAtBetweenAndSitesDeletedIsFalseAndCreatedBy(from.atStartOfDay(), to.plusDays(1).atStartOfDay(), createdBy, pageable);
        }
    }

 }




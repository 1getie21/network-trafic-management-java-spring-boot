package com.insa.TeamOpsSystem.sixmonthchekelist;


import com.insa.TeamOpsSystem.CheckList.CheckList;
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

import static com.insa.TeamOpsSystem.jwt.until.Util.getNullPropertyNames;


@Service
@RequiredArgsConstructor
public class SixMCListService {
    private final SixMCListRepository sixmclistRepository;


    public SixMCList createTraffics(SixMCList sixmclist, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        UserDetails userDetails = (UserDetails) token.getPrincipal();
        sixmclist.setCreatedBy(userDetails.getUsername());
        sixmclist.setUpdated_by(userDetails.getUsername());
        return  sixmclistRepository.save(sixmclist);
    }


    public SixMCList getTrafficById(long id) {
        return sixmclistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(SixMCList.class, "  Type with an id: " + id + " was not found!"));
    }


    public Page<SixMCList> getAllTraffics(Pageable pageable, UsernamePasswordAuthenticationToken token) {

        UserDetailsImpl userDetails = (UserDetailsImpl) token.getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return sixmclistRepository.findAllBySitesDeletedIsFalse(pageable);
        } else {
            return sixmclistRepository.findAllByCreatedByAndSitesDeletedIsFalseOrderByCreatedAtDesc(userDetails.getUsername(), pageable);
        }
    }

    public SixMCList updateTrafficById(long id, SixMCList sixmclist, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        var et = getTrafficById(id);

        BeanUtils.copyProperties(sixmclist, et, getNullPropertyNames(sixmclist));


        return sixmclistRepository.save(et);

    }

    public void deleteTrafficById(long id, UsernamePasswordAuthenticationToken token) {
        sixmclistRepository.deleteById(id);
    }

    public Page<SixMCList> findAllByCreatedAtBetween(LocalDate from, LocalDate to, Pageable pageable) {
        return sixmclistRepository.findAllByCreatedAtBetween(
                from.atStartOfDay(),
                to.plusDays(1).atStartOfDay(),pageable);
    }

}

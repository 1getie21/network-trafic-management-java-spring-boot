package com.insa.TeamOpsSystem.sites;


import com.insa.TeamOpsSystem.exceptions.EntityNotFoundException;
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
public class SitesServiceImpl implements SitesService {
    private final SitesRepository sitesRepository;

    @Override
    public Sites createTraffics(Sites sites, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        UserDetails userDetails = (UserDetails) token.getPrincipal();
        sites.setCreatedBy(userDetails.getUsername());
        sites.setUpdated_by(userDetails.getUsername());
        return sitesRepository.save(sites);
    }

    @Override
    public Sites getTrafficById(long id) {
        return sitesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Sites.class, "  Type with an id: " + id + " was not found!"));
    }


    @Override
    public Page<Sites> getAllTraffics(Pageable pageable) {
        return sitesRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Override
    public Sites updateTrafficById(long id, Sites sites, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        var et = getTrafficById(id);

        BeanUtils.copyProperties(sites, et, getNullPropertyNames(sites));
        return sitesRepository.save(et);
    }

    @Override
    public void deleteTrafficById(long id, UsernamePasswordAuthenticationToken token) {
        sitesRepository.deleteById(id);
    }


}

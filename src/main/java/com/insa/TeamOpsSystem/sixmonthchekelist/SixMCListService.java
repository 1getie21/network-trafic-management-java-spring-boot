package com.insa.TeamOpsSystem.sixmonthchekelist;


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
        return sixmclistRepository.findAllBySitesDeletedIsFalse(pageable);

    }

    public SixMCList updateTrafficById(long id, SixMCList sixmclist, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        var et = getTrafficById(id);

        BeanUtils.copyProperties(sixmclist, et, getNullPropertyNames(sixmclist));


        return sixmclistRepository.save(et);

    }

    public void deleteTrafficById(long id, UsernamePasswordAuthenticationToken token) {
        sixmclistRepository.deleteById(id);
    }


}

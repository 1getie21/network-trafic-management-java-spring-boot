package com.insa.TeamOpsSystem.CheckList;


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
public class CheckListService {
    private final CheckListRepository checkListRepository;


    public CheckList createTraffics(CheckList failedTraffics, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        UserDetails userDetails = (UserDetails) token.getPrincipal();
        failedTraffics.setCreatedBy(userDetails.getUsername());
        failedTraffics.setUpdated_by(userDetails.getUsername());
        failedTraffics.setNbpTotal(
                Float.parseFloat(failedTraffics.getNpbone())+
                        Float.parseFloat(failedTraffics.getNpbtwo())+
                        Float.parseFloat(failedTraffics.getNpbthree()));
        failedTraffics.setAvgNBP(String.valueOf(failedTraffics.getNbpTotal()/3));

        return checkListRepository.save(failedTraffics);
    }


    public CheckList getTrafficById(long id) {
        return checkListRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CheckList.class, "  Type with an id: " + id + " was not found!"));
    }



    public Page<CheckList> getAllCheckLists(Pageable pageable) {
       return checkListRepository.findAllBySitesDeletedIsFalse(pageable);

    }

    public CheckList updateCheckListById(long id, CheckList failedTraffics, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        var et = getTrafficById(id);
        failedTraffics.setNbpTotal(
                Float.parseFloat(failedTraffics.getNpbone())+
                        Float.parseFloat(failedTraffics.getNpbtwo())+
                        Float.parseFloat(failedTraffics.getNpbthree()));
        failedTraffics.setAvgNBP(String.valueOf(failedTraffics.getNbpTotal()/3));
        BeanUtils.copyProperties(failedTraffics, et, getNullPropertyNames(failedTraffics));


        return checkListRepository.save(et);
    }


    public void deleteCheckListById(long id, UsernamePasswordAuthenticationToken token) {
        checkListRepository.deleteById(id);
    }


}

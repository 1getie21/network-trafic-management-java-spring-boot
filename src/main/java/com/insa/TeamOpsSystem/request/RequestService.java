package com.insa.TeamOpsSystem.request;


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
public class RequestService {
    private final RequestRepository requestRepository;


    public Request createTraffics(Request request, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        UserDetails userDetails = (UserDetails) token.getPrincipal();
        request.setCreatedBy(userDetails.getUsername());
        request.setUpdated_by(userDetails.getUsername());
        Request save;
        save = requestRepository.<Request>save(request);
        return save;
    }


    public Request getTrafficById(long id) {
        return requestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Request.class, "  Type with an id: " + id + " was not found!"));
    }

    public Page<Request> getAllTraffics(Pageable pageable, UsernamePasswordAuthenticationToken token) {
        UserDetailsImpl userDetails = (UserDetailsImpl) token.getPrincipal();
        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {

            return requestRepository.findAll(pageable);
        } else {
            return requestRepository.findAllByCreatedByOrderByCreatedAtDesc(userDetails.getUsername(), pageable);
        }
    }

    public Request updateTrafficById(long id, Request request, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        var et = getTrafficById(id);

        BeanUtils.copyProperties(request, et, getNullPropertyNames(request));


        return requestRepository.save(et);

    }


    public void deleteTrafficById(long id, UsernamePasswordAuthenticationToken token) {
        requestRepository.deleteById(id);
    }


}

package com.insa.TeamOpsSystem.failedTraffics;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

public interface FailedTrafficApi {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    FailedTrafficDtos createTraffics(@RequestBody @Valid FailedTrafficDtos failedTrafficDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    FailedTrafficDtos getTrafficById(@PathVariable("id") long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    FailedTrafficDtos updateTrafficById(@PathVariable("id") long trafficId, @RequestBody @Valid FailedTrafficDtos failedTrafficDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTrafficById(@PathVariable("id") long trafficId, UsernamePasswordAuthenticationToken token) throws IllegalAccessException;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PagedModel<FailedTrafficDtos>> getAllTraffics(Pageable pageable, UsernamePasswordAuthenticationToken token,
                                                                 PagedResourcesAssembler assembler,
                                                                 UriComponentsBuilder uriBuilder,
                                                                 final HttpServletResponse response);
}

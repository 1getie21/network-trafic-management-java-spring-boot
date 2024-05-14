package com.insa.TeamOpsSystem.sites;

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

public interface SitesApi {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    SitesDtos createTraffics(@RequestBody @Valid SitesDtos sitesDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    SitesDtos getTrafficById(@PathVariable("id") long id);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    SitesDtos updateTrafficById(@PathVariable("id") long trafficId, @RequestBody @Valid SitesDtos sitesDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException;
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTrafficById(@PathVariable("id") long trafficId,UsernamePasswordAuthenticationToken token) throws IllegalAccessException;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PagedModel<SitesDtos>> getAllTraffics(@Parameter(description = "pagination object", schema = @Schema(implementation = Pageable.class))
                                                       @Valid Pageable pageable,
                                                         PagedResourcesAssembler assembler,
                                                         UriComponentsBuilder uriBuilder,
                                                         final HttpServletResponse response);
}

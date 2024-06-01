package com.insa.TeamOpsSystem.request;


import com.insa.TeamOpsSystem.jwt.PaginatedResultsRetrievedEvent;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final RequestMapper requestMapper;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    Request createTraffics(@RequestBody @Valid Request requestDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException{
        return requestService.createTraffics(requestDtos,token);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Request getTrafficById(@PathVariable("id") long id) {
        return requestService.getTrafficById(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Request updateTrafficById(@PathVariable("id") long id, @RequestBody @Valid Request requestDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return requestService.updateTrafficById(id,requestDtos,token);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTrafficById(@PathVariable("id") long id,UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
           requestService.deleteTrafficById(id,token);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PagedModel<RequestDtos>> getAllTraffics(@Parameter(description = "pagination object", schema = @Schema(implementation = Pageable.class))
                                                           @Valid Pageable pageable, UsernamePasswordAuthenticationToken token,
                                                           PagedResourcesAssembler assembler,
                                                           UriComponentsBuilder uriBuilder,
                                                           final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                RequestDtos.class, uriBuilder, response, pageable.getPageNumber(), requestService.getAllTraffics(pageable,token).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<RequestDtos>>(assembler.toModel(requestService.getAllTraffics(pageable,token).map(requestMapper::toTrafficsDto)), HttpStatus.OK);
    }
}

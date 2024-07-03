package com.insa.TeamOpsSystem.user.request;

import com.insa.TeamOpsSystem.jwt.PaginatedResultsRetrievedEvent;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final RequestMapper requestMapper;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    Request createTraffics(@RequestBody @Valid Request requestDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException{
        return requestService.createTraffics(requestDtos,token);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Request getTrafficById(@PathVariable("id") long id) {
        return requestService.getTrafficById(id);
    }
    @GetMapping("/accept/{id}")
    @ResponseStatus(HttpStatus.OK)
    Request acceptRequest(@PathVariable("id") long id,UsernamePasswordAuthenticationToken token) {
        return requestService.acceptRequest(id,token);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    Request updateTrafficById(@PathVariable("id") long id, @RequestBody @Valid Request requestDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return requestService.updateTrafficById(id,requestDtos,token);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
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

    @GetMapping("/{from}/{to}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PagedModel<RequestDtos>> findAllByCreatedAtBetween(
            @PathVariable("from")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from
            , @PathVariable("to")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
            , Pageable pageable,
            PagedResourcesAssembler assembler,
            UriComponentsBuilder uriBuilder,
            final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                RequestDtos.class, uriBuilder, response, pageable.getPageNumber(), requestService.findAllByCreatedAtBetween(from,to, pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<RequestDtos>>(assembler.toModel(requestService.findAllByCreatedAtBetween(from,to, pageable).map(requestMapper::toTrafficsDto)), HttpStatus.OK);
    }
}

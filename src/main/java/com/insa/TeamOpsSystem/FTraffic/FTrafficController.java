package com.insa.TeamOpsSystem.FTraffic;

import com.insa.TeamOpsSystem.jwt.PaginatedResultsRetrievedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/f-traffics")
@RequiredArgsConstructor
@Component("FTrafficController") // Specify a unique name for this controller
public class FTrafficController {
    private final FTrafficService fTrafficService;
    private final FTrafficMapper fTrafficMapper;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public FTrafficDtos createTraffics(@RequestBody @Valid FTrafficDtos fTrafficDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return fTrafficMapper.toTrafficsDto(fTrafficService.createTraffics(fTrafficMapper.toTraffics(fTrafficDtos), token));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FTrafficDtos getTrafficById(@PathVariable("id") long id) {
        return fTrafficMapper.toTrafficsDto(fTrafficService.getTrafficById(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FTrafficDtos updateTrafficById(@PathVariable("id") long trafficId, @RequestBody @Valid FTrafficDtos fTrafficDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return fTrafficMapper.toTrafficsDto(fTrafficService.updateTrafficById(trafficId, fTrafficMapper.toTraffics(fTrafficDtos), token));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteTrafficById(@PathVariable("id") long trafficId,
                           UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        fTrafficService.deleteTrafficById(trafficId, token);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PagedModel<FTrafficDtos>> getAllTraffics(Pageable pageable, UsernamePasswordAuthenticationToken token,
                                                            PagedResourcesAssembler assembler,
                                                            UriComponentsBuilder uriBuilder,
                                                            final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                FTrafficDtos.class, uriBuilder, response, pageable.getPageNumber(), fTrafficService.getAllTraffics(pageable, token).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<FTrafficDtos>>(assembler.toModel(fTrafficService.getAllTraffics(pageable, token).map(fTrafficMapper::toTrafficsDto)), HttpStatus.OK);
    }

    @GetMapping("/tr/{timeTraffic}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PagedModel<FTrafficDtos>> getAllTrafficsByTrafficTime(@PathVariable("timeTraffic") String timeTraffic, Pageable pageable,
                                                                         PagedResourcesAssembler assembler,
                                                                         UriComponentsBuilder uriBuilder,
                                                                         final HttpServletResponse response,
                                                                     UsernamePasswordAuthenticationToken token) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                FTrafficDtos.class, uriBuilder, response, pageable.getPageNumber(), fTrafficService.getAllTrafficsByTrafficTime(timeTraffic, token, pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<FTrafficDtos>>(assembler.toModel(fTrafficService.getAllTrafficsByTrafficTime(timeTraffic, token, pageable).map(fTrafficMapper::toTrafficsDto)), HttpStatus.OK);
    }

//
@GetMapping("/{from}/{to}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PagedModel<FTrafficDtos>> findAllByCreatedAtBetween(
            @PathVariable("from")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from
            , @PathVariable("to")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
            ,UsernamePasswordAuthenticationToken token, Pageable pageable,
           @RequestParam(name = "trafficTime", required = false) String trafficTime,
            PagedResourcesAssembler assembler,
            UriComponentsBuilder uriBuilder,
            final HttpServletResponse response) {

        Page<Ftraffics> page = fTrafficService.getAllTrafficsByTrafficTime(trafficTime, token, pageable);

        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                FTrafficDtos.class, uriBuilder, response, pageable.getPageNumber(), fTrafficService.findAllByCreatedAtBetween(from,to,token,pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<FTrafficDtos>>(assembler.toModel(fTrafficService.findAllByCreatedAtBetween(from,to,token, pageable).map(fTrafficMapper::toTrafficsDto)), HttpStatus.OK);
    }

}

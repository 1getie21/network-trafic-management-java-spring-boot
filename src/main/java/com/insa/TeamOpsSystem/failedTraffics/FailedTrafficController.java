package com.insa.TeamOpsSystem.failedTraffics;


import com.insa.TeamOpsSystem.jwt.PaginatedResultsRetrievedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/failed-traffics")
@RequiredArgsConstructor
public class FailedTrafficController implements FailedTrafficApi {
    private final FailedTrafficService failedTrafficService;
    private final FailedTrafficMapper failedTrafficMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public FailedTrafficDtos createTraffics(FailedTrafficDtos failedTrafficDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return failedTrafficMapper.toTrafficsDto(failedTrafficService.createTraffics(failedTrafficMapper.toTraffics(failedTrafficDtos),token));
    }

    @Override
    public FailedTrafficDtos getTrafficById(long id) {
        return failedTrafficMapper.toTrafficsDto(failedTrafficService.getTrafficById(id));
    }

    @Override
    public FailedTrafficDtos updateTrafficById(long id, FailedTrafficDtos failedTrafficDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return failedTrafficMapper.toTrafficsDto(failedTrafficService.updateTrafficById(id, failedTrafficMapper.toTraffics(failedTrafficDtos), token));
    }

    @Override
    public void deleteTrafficById(long trafficId, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
           failedTrafficService.deleteTrafficById(trafficId,token);
    }

    @Override
    public ResponseEntity<PagedModel<FailedTrafficDtos>> getAllTraffics(Pageable pageable,UsernamePasswordAuthenticationToken token, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                FailedTrafficDtos.class, uriBuilder, response, pageable.getPageNumber(), failedTrafficService.getAllTraffics(pageable,token).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<FailedTrafficDtos>>(assembler.toModel(failedTrafficService.getAllTraffics(pageable,token).map(failedTrafficMapper::toTrafficsDto)), HttpStatus.OK);
    }
}

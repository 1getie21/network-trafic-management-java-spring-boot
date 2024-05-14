package com.insa.TeamOpsSystem.traffics;

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
@RequestMapping("/traffics")
@RequiredArgsConstructor
public class TrafficController implements TrafficApi {
    private final TrafficService trafficService;
    private final TrafficMapper trafficMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public TrafficDtos createTraffics(TrafficDtos trafficDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return trafficMapper.toTrafficsDto(trafficService.createTraffics(trafficMapper.toTraffics(trafficDtos),token));
    }

    @Override
    public TrafficDtos getTrafficById(long id) {
        return trafficMapper.toTrafficsDto(trafficService.getTrafficById(id));
    }

    @Override
    public TrafficDtos updateTrafficById(long id, TrafficDtos trafficDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return trafficMapper.toTrafficsDto(trafficService.updateTrafficById(id, trafficMapper.toTraffics(trafficDtos), token));
    }

    @Override
    public void deleteTrafficById(long trafficId, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
           trafficService.deleteTrafficById(trafficId,token);
    }

    @Override
    public ResponseEntity<PagedModel<TrafficDtos>> getAllTraffics(Pageable pageable,UsernamePasswordAuthenticationToken token, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                TrafficDtos.class, uriBuilder, response, pageable.getPageNumber(), trafficService.getAllTraffics(pageable,token).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<TrafficDtos>>(assembler.toModel(trafficService.getAllTraffics(pageable,token).map(trafficMapper::toTrafficsDto)), HttpStatus.OK);
    }
}

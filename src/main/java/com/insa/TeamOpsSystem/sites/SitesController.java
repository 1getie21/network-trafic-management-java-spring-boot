package com.insa.TeamOpsSystem.sites;

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
@RequestMapping("/sites")
@RequiredArgsConstructor
public class SitesController implements SitesApi {
    private final SitesService sitesService;
    private final SitesMapper sitesMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public SitesDtos createTraffics(SitesDtos sitesDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return sitesMapper.toTrafficsDto(sitesService.createTraffics(sitesMapper.toTraffics(sitesDtos),token));
    }

    @Override
    public SitesDtos getTrafficById(long id) {
        return sitesMapper.toTrafficsDto(sitesService.getTrafficById(id));
    }



    @Override
    public SitesDtos updateTrafficById(long id, SitesDtos sitesDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return sitesMapper.toTrafficsDto(sitesService.updateTrafficById(id, sitesMapper.toTraffics(sitesDtos), token));
    }

    @Override
    public void deleteTrafficById(long trafficId, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
           sitesService.deleteTrafficById(trafficId,token);
    }

    @Override
    public ResponseEntity<PagedModel<SitesDtos>> getAllTraffics(Pageable pageable, PagedResourcesAssembler assembler, UriComponentsBuilder uriBuilder, HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                SitesDtos.class, uriBuilder, response, pageable.getPageNumber(), sitesService.getAllTraffics(pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<SitesDtos>>(assembler.toModel(sitesService.getAllTraffics(pageable).map(sitesMapper::toTrafficsDto)), HttpStatus.OK);
    }
}

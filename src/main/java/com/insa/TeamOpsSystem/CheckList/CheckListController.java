package com.insa.TeamOpsSystem.CheckList;


import com.insa.TeamOpsSystem.jwt.PaginatedResultsRetrievedEvent;
import com.insa.TeamOpsSystem.request.RequestDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("/check_list")
@RequiredArgsConstructor
public class CheckListController {
    private final CheckListMapper checkListMapper;
    private final CheckListService checkListService;
    private final ApplicationEventPublisher eventPublisher;
    private long CheckListDtos;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    CheckList createTraffics(@RequestBody @Valid CheckList checkList, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return checkListService.createTraffics(checkList, token);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CheckList getTrafficById(@PathVariable("id") long id) {
        return checkListService.getTrafficById(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    CheckList getCheckListById(@PathVariable("id") long id,
                               @RequestBody @Valid CheckList CheckListDtos, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return checkListService.updateCheckListById(id, CheckListDtos, token);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void deleteCheckListById(@PathVariable("id") long trafficId, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        checkListService.deleteCheckListById(trafficId, token);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<PagedModel<CheckListDtos>> getAllCheckLists(
            UsernamePasswordAuthenticationToken token
            , Pageable pageable
            , PagedResourcesAssembler assembler
            , UriComponentsBuilder uriBuilder
            , final HttpServletResponse response) {
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<>(
                CheckListDtos.class
                , uriBuilder
                , response
                , pageable.getPageNumber()
                , checkListService.getAllCheckLists(token, pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<CheckListDtos>>(assembler.toModel(checkListService.getAllCheckLists(token, pageable).map(checkListMapper::toTrafficsDto)), HttpStatus.OK);
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
                RequestDtos.class, uriBuilder, response, pageable.getPageNumber(), checkListService.findAllByCreatedAtBetween(from,to, pageable).getTotalPages(), pageable.getPageSize()));
        return new ResponseEntity<PagedModel<RequestDtos>>(assembler.toModel(checkListService.findAllByCreatedAtBetween(from,to, pageable).map(checkListMapper::toTrafficsDto)), HttpStatus.OK);
    }
}

package com.insa.TeamOpsSystem.sixmonthchekelist;

import com.insa.TeamOpsSystem.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.insa.TeamOpsSystem.until.Util.getNullPropertyNames;

@RestController
@RequestMapping("/SixMCList")
@RequiredArgsConstructor
public class SixMCListController {
    private final SixMCListService sixMCListService;

    @PostMapping
    public SixMCList createTraffics(@RequestBody SixMCList sixmclist, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return sixMCListService.createTraffics(sixmclist,token);
    }

    @GetMapping("/{id}")
    public SixMCList getTrafficById(@PathVariable("id") long id) {
        return sixMCListService.getTrafficById(id);}

    @GetMapping
    public Page<SixMCList> getAllTraffics(Pageable pageable, UsernamePasswordAuthenticationToken token) {
        return sixMCListService.getAllTraffics(pageable,token);
    }

    @PutMapping("/{id}")
    public SixMCList updateTrafficById(@PathVariable("id") long id, SixMCList sixmclist, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {

        return sixMCListService.updateTrafficById(id,sixmclist,token);

    }

    @DeleteMapping("/{id}")
    public void deleteTrafficById(@PathVariable("id") long id, UsernamePasswordAuthenticationToken token) {
        sixMCListService.deleteTrafficById(id,token);
    }
}

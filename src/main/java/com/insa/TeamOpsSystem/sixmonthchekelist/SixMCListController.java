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
@RequestMapping("/sixmclist")
@RequiredArgsConstructor
public class SixMCListController {
    private final SixMCListService sixmclistService;

    @PostMapping
    public SixMCList createTraffics(@RequestBody SixMCList sixmclist, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {
        return sixmclistService.createTraffics(sixmclist,token);
    }

    @GetMapping("/{id}")
    public SixMCList getTrafficById(@PathVariable("id") long id) {
        return sixmclistService.getTrafficById(id);}

    @GetMapping
    public Page<SixMCList> getAllTraffics(Pageable pageable, UsernamePasswordAuthenticationToken token) {
        return sixmclistService.getAllTraffics(pageable,token);
    }

    @PutMapping("/{id}")
    public SixMCList updateTrafficById(@PathVariable("id") long id,@RequestBody SixMCList sixmclist, UsernamePasswordAuthenticationToken token) throws IllegalAccessException {

        return sixmclistService.updateTrafficById(id,sixmclist,token);
    }

    @DeleteMapping("/{id}")
    public void deleteTrafficById(@PathVariable("id") long id, UsernamePasswordAuthenticationToken token) {
        sixmclistService.deleteTrafficById(id,token);
    }
}

package com.micro.cast.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.cast.dto.CastingDto;
import com.micro.cast.services.CastingService;

@RestController
@RequestMapping("/casting")
public class CastingController {

    private final CastingService castingService;

    @Autowired
    public CastingController(CastingService castingService) {
        this.castingService = castingService;
    }

    
    // http://localhost:4003/casting/all
    @GetMapping("/all")
    public List<CastingDto> getAllCastings() {
        return castingService.getAllCastings();
    }

    //  Get casting by Id
    // http://localhost:4003/casting/{castingId}
    @GetMapping("/{castingId}")
    public CastingDto getCastingById(@PathVariable String castingId) {
        return castingService.getCastingById(castingId);
    }

    //  Get casting by Actor Name
    // http://localhost:4003/casting/actor/{actorName}
    @GetMapping("/actor/{actorName}")
    public CastingDto getCastingByActorName(@PathVariable String actorName) {
        return castingService.getCastingByActorName(actorName);
    }

    //  Add new casting
    // POST http://localhost:4003/casting
    @PostMapping
    public String addCasting(@RequestBody CastingDto castingDto) {
        return castingService.addCasting(castingDto);
    }

    //  Get total casting count
    // http://localhost:4003/casting/count
    @GetMapping("/count")
    public Integer getCastingCount() {
        return castingService.getCastingCount();
    }
    @PostMapping("/addWithProject")
public String addCastingWithProject(@RequestBody CastingDto requestDto) {
    return castingService.addCastingWithProject(requestDto);
}

}

package com.micro.cast.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.micro.cast.dto.CastingDto;
import com.micro.cast.dto.ProjectDto;
import com.micro.cast.irepository.ICastingRepo;
import com.micro.cast.models.Casting;

@Service(value = "CastingService")
public class CastingService {

    private final ICastingRepo _castingRepo;
    private final ModelMapper _modelMapper;
    private final RestClient _projectRestClient;

    @Autowired
    public CastingService(ICastingRepo castingRepo, 
                          ModelMapper modelMapper,
                          RestClient.Builder restClientBuilder) {
        this._castingRepo = castingRepo;
        this._modelMapper = modelMapper;

        // ✅ Only ProjectService base URL
        _projectRestClient = restClientBuilder.baseUrl("http://localhost:4002/projects").build();
    }

    // ✅ Get all castings
   public List<CastingDto> getAllCastings() {
    List<Casting> castings = _castingRepo.GetAllCastings();
    List<CastingDto> castingDtos = new ArrayList<>();

    for (Casting casting : castings) {
        CastingDto dto = _modelMapper.map(casting, CastingDto.class);

        // ✅ fetch project details by castingId (via projectId inside casting)
        List<ProjectDto> projects = getProjectsByCastingId(casting.getCastingId());
        dto.setProjects(projects); // no more null

        castingDtos.add(dto);
    }
    return castingDtos;
}


    // ✅ Get casting by Id
    public CastingDto getCastingById(String castingId) {
        Casting casting = _castingRepo.GetCastingById(castingId);
        if (casting == null) return null;

        CastingDto dto = _modelMapper.map(casting, CastingDto.class);
        dto.setProjects(getProjectsByCastingId(castingId));
        return dto;
    }

    // ✅ Get casting by actor name
    public CastingDto getCastingByActorName(String actorName) {
        Casting casting = _castingRepo.GetCastingByActorName(actorName);
        if (casting == null) return null;

        CastingDto dto = _modelMapper.map(casting, CastingDto.class);
        dto.setProjects(getProjectsByCastingId(casting.getCastingId()));
        return dto;
    }

    // ✅ Add new casting
    public String addCasting(CastingDto castingDto) {
        Casting casting = _modelMapper.map(castingDto, Casting.class);
        _castingRepo.AddCasting(casting);
        return casting.getCastingId();
    }

    // ✅ Get total casting count
    public Integer getCastingCount() {
        return _castingRepo.GetCastingCount();
    }

    // ✅ Helper: call ProjectService
   private List<ProjectDto> getProjectsByCastingId(String castingId) {
    try {
        // step 1: find casting
        Casting casting = _castingRepo.GetCastingById(castingId);
        if (casting == null || casting.getProjectId() == null) {
            return Collections.emptyList();
        }

        // step 2: fetch project by projectId
        ProjectDto project = _projectRestClient.get()
            .uri("/project/{projectId}", casting.getProjectId())
            .retrieve()
            .body(ProjectDto.class);

        if (project != null) {
            return List.of(project); // wrap in list
        }
        return Collections.emptyList();

    } catch (Exception e) {
        System.out.println("Error fetching project: " + e.getMessage());
        return Collections.emptyList();
    }
}
public String addCastingWithProject(CastingDto requestDto) {
    // 1. Save casting
    Casting casting = _modelMapper.map(requestDto, Casting.class);
    _castingRepo.AddCasting(casting);

    // 2. If project info is provided, forward it to ProjectService
    if (requestDto.getProjects() != null && !requestDto.getProjects().isEmpty()) {
        try {
            _projectRestClient.post()
                .uri("/add")  // assumes ProjectService has POST /projects/add
                .body(requestDto.getProjects().get(0))  // take first project
                .retrieve()
                .toBodilessEntity();
        } catch (Exception e) {
            System.out.println("Error while saving project in ProjectService: " + e.getMessage());
        }
    }

    return casting.getCastingId();
}

}

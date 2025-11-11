package com.micro.cast.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException; // Import this
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException; // Import this
import org.springframework.web.client.ResourceAccessException; // Import this
import org.springframework.web.client.RestClient;

import com.micro.cast.dto.CastingDto;
import com.micro.cast.dto.ProjectDto;
import com.micro.cast.exception.CastingNotFoundException;
import com.micro.cast.exception.InvalidCastingException;
import com.micro.cast.exception.ProjectServiceOfflineException;
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
        _projectRestClient = restClientBuilder.baseUrl("http://localhost:4002/projects").build();
    }

    /**
     * Get all castings.
     * Can throw ProjectServiceOfflineException if the project service is down.
     */
    public List<CastingDto> getAllCastings() throws ProjectServiceOfflineException {
        List<Casting> castings = _castingRepo.GetAllCastings();
        List<CastingDto> castingDtos = new ArrayList<>();

        for (Casting casting : castings) {
            CastingDto dto = _modelMapper.map(casting, CastingDto.class);
            // This helper method now throws an exception, which propagates up
            List<ProjectDto> projects = getProjectsByCastingId(casting.getCastingId());
            dto.setProjects(projects);
            castingDtos.add(dto);
        }
        return castingDtos;
    }

    /**
     * Get casting by Id.
     * Throws CastingNotFoundException or ProjectServiceOfflineException.
     */
    public CastingDto getCastingById(String castingId) throws CastingNotFoundException, ProjectServiceOfflineException {
        Casting casting;
        try {
            casting = _castingRepo.GetCastingById(castingId);
        } catch (EmptyResultDataAccessException e) {
            // CATCH the repo exception and THROW our custom one.
            throw new CastingNotFoundException("Casting not found with ID: " + castingId);
        }

        CastingDto dto = _modelMapper.map(casting, CastingDto.class);
        dto.setProjects(getProjectsByCastingId(castingId));
        return dto;
    }

    /**
     * Get casting by actor name.
     * Throws CastingNotFoundException or ProjectServiceOfflineException.
     */
    public CastingDto getCastingByActorName(String actorName) throws CastingNotFoundException, ProjectServiceOfflineException {
        Casting casting;
        try {
            casting = _castingRepo.GetCastingByActorName(actorName);
        } catch (EmptyResultDataAccessException e) {
            // CATCH the repo exception and THROW our custom one.
            throw new CastingNotFoundException("Casting not found for actor: " + actorName);
        }

        CastingDto dto = _modelMapper.map(casting, CastingDto.class);
        dto.setProjects(getProjectsByCastingId(casting.getCastingId()));
        return dto;
    }

    /**
     * Add new casting.
     * Throws InvalidCastingException for bad data.
     */
    public Casting addCasting(CastingDto castingDto) throws InvalidCastingException {
        // --- VALIDATION (from assignment Example 2) ---
        if (castingDto.getActorName() == null || castingDto.getActorName().trim().isEmpty()) {
            throw new InvalidCastingException("Bad request: Actor name cannot be null or empty.");
        }
        if (castingDto.getRole() == null || castingDto.getRole().trim().isEmpty()) {
            throw new InvalidCastingException("Bad request: Role cannot be null or empty.");
        }
        if (castingDto.getProjectId() == null || castingDto.getProjectId().trim().isEmpty()) {
            throw new InvalidCastingException("Bad request: ProjectID cannot be null or empty.");
        }
        // --- End Validation ---

        Casting casting = _modelMapper.map(castingDto, Casting.class);
        // We return the saved Casting object, not just the ID.
        return _castingRepo.AddCasting(casting); 
    }

    /**
     * Get total casting count.
     * (This is a simple query, likely no exceptions needed)
     */
    public Integer getCastingCount() {
        return _castingRepo.GetCastingCount();
    }

    /**
     * Helper: call ProjectService.
     * This is the "innovative" part. We catch specific RestClient exceptions.
     */
    private List<ProjectDto> getProjectsByCastingId(String castingId) throws ProjectServiceOfflineException {
        Casting casting;
        try {
            casting = _castingRepo.GetCastingById(castingId);
        } catch (EmptyResultDataAccessException e) {
            // This is a helper method, if casting isn't found, just return empty.
            return Collections.emptyList();
        }
        
        if (casting.getProjectId() == null) {
            return Collections.emptyList();
        }

        try {
            // step 2: fetch project by projectId
            ProjectDto project = _projectRestClient.get()
                    .uri("/project/{projectId}", casting.getProjectId())
                    .retrieve()
                    .body(ProjectDto.class);

            if (project != null) {
                return List.of(project); // wrap in list
            }
            return Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            // Project service is ON, but the specific project ID was not found. This is OK.
            System.out.println("Project not found for projectId: " + casting.getProjectId());
            return Collections.emptyList();
        } catch (ResourceAccessException e) {
            // THIS IS THE KEY: The service is DOWN.
            // Throw our custom exception so the controller can catch it.
            throw new ProjectServiceOfflineException("Project Service is offline. " + e.getMessage());
        } catch (Exception e) {
            // Catch other unexpected errors
            System.out.println("Error fetching project: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Add casting AND project.
     * Throws InvalidCastingException or ProjectServiceOfflineException.
     */
    public Casting addCastingWithProject(CastingDto requestDto) throws InvalidCastingException, ProjectServiceOfflineException {
        // 1. Run validation and save casting
        // This will throw InvalidCastingException if data is bad
        Casting casting = addCasting(requestDto); 

        // 2. If project info is provided, forward it to ProjectService
        if (requestDto.getProjects() != null && !requestDto.getProjects().isEmpty()) {
            try {
                _projectRestClient.post()
                        .uri("/add") // assumes ProjectService has POST /projects/add
                        .body(requestDto.getProjects().get(0)) // take first project
                        .retrieve()
                        .toBodilessEntity();
            } catch (ResourceAccessException e) {
                // The service is DOWN.
                throw new ProjectServiceOfflineException("Project Service is offline, could not save project. " + e.getMessage());
            } catch (Exception e) {
                // The service is ON, but something else went wrong (e.g., 400 Bad Request)
                System.out.println("Error while saving project in ProjectService: " + e.getMessage());
                // We don't re-throw here, as the casting was still saved.
                // This is a business logic decision.
            }
        }
        return casting;
    }
}
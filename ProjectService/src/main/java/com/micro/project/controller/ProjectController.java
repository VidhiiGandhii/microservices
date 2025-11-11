package com.micro.project.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.micro.project.dto.ProjectDto;
import com.micro.project.dto.ProjectResponseDto;
import com.micro.project.exceptions.InvalidProjectException;
import com.micro.project.exceptions.ProjectAlreadyExistsException;
import com.micro.project.exceptions.ProjectNotFoundException;
import com.micro.project.models.Project;
import com.micro.project.services.ProjectService;

@RequestMapping("/projects")
@RestController
public class ProjectController {
    
    private final ProjectService _ProjectService;
    private final ModelMapper _modelMapper; // For mapping back

    @Autowired
    public ProjectController(ProjectService ProjectService, ModelMapper modelMapper) {
        this._ProjectService = ProjectService;
        this._modelMapper = modelMapper;
    }

    /**
     * GET /projects/allprojects
     * Returns a list of all projects.
     */
    @GetMapping(path = "/allprojects")
    public ResponseEntity<ProjectResponseDto> getAllProjects() {
        ProjectResponseDto response = new ProjectResponseDto();
        try {
            List<ProjectDto> projects = _ProjectService.GetAllProjects();
            
            response.set_Status("Success");
            response.set_Projects(projects);
            return ResponseEntity.ok(response);

        } catch (DataAccessException e) { // Catch DB Down
            response.set_Status("Service error: Could not access database. " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        } catch (Exception e) { // Catch any other error
            response.set_Status("An internal server error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * GET /projects/project/{project_id}
     * Returns a single project by its ID.
     */
    @GetMapping(path = "/project/{project_id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable("project_id") String ProjectId) {
        ProjectResponseDto response = new ProjectResponseDto();
        try {
            ProjectDto project = _ProjectService.GetProjectById(ProjectId);
            
            response.set_Status("Success");
            response.set_Project(project);
            return ResponseEntity.ok(response);

        } catch (ProjectNotFoundException ex) {
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.set_Status("An internal server error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    /**
     * POST /projects/project
     * Adds a new project.
     */
    @PostMapping("/project")
    public ResponseEntity<ProjectResponseDto> addProject(@RequestBody ProjectDto ProjectDto) {
        ProjectResponseDto response = new ProjectResponseDto();
        try {
            Project savedProject = _ProjectService.AddProject(ProjectDto);
            
            response.set_Status("Success: Project created with ID: " + savedProject.get_projectId());
            response.set_Project(_modelMapper.map(savedProject, ProjectDto.class));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (InvalidProjectException ex) {
            response.set_Status(ex.getMessage());
            return ResponseEntity.badRequest().body(response); // 400
        } catch (ProjectAlreadyExistsException ex) {
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response); // 409
        } catch (DataAccessException e) {
            response.set_Status("Service error: Could not save to database. " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response); // 503
        } catch (Exception e) {
            response.set_Status("An internal server error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response); // 500
        }
    }

    // --- You can apply this same try-catch pattern to your other GET endpoints ---

    @GetMapping(path = "/project/projectTitle/{project_id}")
    public ResponseEntity<ProjectResponseDto> getProjectTitleById(@PathVariable("project_id") String Projectid) {
        ProjectResponseDto response = new ProjectResponseDto();
        try {
            String title = _ProjectService.GetProjectTitleById(Projectid);
            response.set_Status("Success");
            response.set_StringResult(title);
            return ResponseEntity.ok(response);
        } catch (ProjectNotFoundException ex) {
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.set_Status("An internal server error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(path = "/project/projectid/{project_title}")
    public ResponseEntity<ProjectResponseDto> getProjectIdByTitle(@PathVariable("project_title") String ProjectTitle) {
        ProjectResponseDto response = new ProjectResponseDto();
        try {
            String id = _ProjectService.GetProjectIdByTitle(ProjectTitle);
            response.set_Status("Success");
            response.set_StringResult(id);
            return ResponseEntity.ok(response);
        } catch (ProjectNotFoundException ex) {
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.set_Status("An internal server error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping(path = "/project")
    public ResponseEntity<ProjectResponseDto> getProjectByTitle(@RequestParam("project_title") String ProjectTitle) {
         ProjectResponseDto response = new ProjectResponseDto();
        try {
            ProjectDto project = _ProjectService.GetProjectByTitle(ProjectTitle);
            response.set_Status("Success");
            response.set_Project(project);
            return ResponseEntity.ok(response);
        } catch (ProjectNotFoundException ex) {
            response.set_Status(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.set_Status("An internal server error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
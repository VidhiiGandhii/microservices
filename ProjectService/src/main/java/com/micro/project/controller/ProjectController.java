package com.micro.project.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.micro.project.dto.ProjectDto;
import com.micro.project.services.ProjectService;
    
@RequestMapping("/projects")
@RestController
public class ProjectController 
{
    private final ProjectService _ProjectService;
    
@Autowired 
    public ProjectController(ProjectService ProjectService) {
        this._ProjectService = ProjectService;
    }
        /*
     * http://localhost:4002/projects/
     */
    @GetMapping(path = "/allprojects")
    public List<ProjectDto> getAllProjects() 
    {
       return _ProjectService.GetAllProjects();
    }
//http://localhost:4002/projects/project/{projectId}
  @GetMapping(path = "/project/{project_id}")
    public ProjectDto getProjectById(@PathVariable("project_id") String ProjectId) 
    {
        return _ProjectService.GetProjectById(ProjectId);
    }
//http://localhost:4002/projects/project/projectTitle/{projectid}
    @GetMapping(path = "/project/projectTitle/{project_id}")
    public String getProjectTitleById(@PathVariable("project_id") String Projectid)
    {
        return _ProjectService.GetProjectTitleById(Projectid);
    }

    @GetMapping(path = "/project/projectid/{project_title}")
    public String getProjectIdByTitle(@PathVariable("project_title") String ProjectTitle)
    {
        return _ProjectService.GetProjectIdByTitle(ProjectTitle);
    }

    @GetMapping(path = "/project")
    public ProjectDto getProjectByTitle(@RequestParam("project_title") String ProjectTitle)
    {
        return _ProjectService.GetProjectByTitle(ProjectTitle);
    }

    @PostMapping("/project")
    public ProjectDto addProject(@RequestBody ProjectDto Project) 
    {
        _ProjectService.AddProject(Project);
        return Project;
    }


}

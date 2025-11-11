package com.micro.project.dto;

import java.util.List;

// This matches your professor's DTO examples.
public class ProjectResponseDto {

    private String _Status;
    private ProjectDto _Project;
    private List<ProjectDto> _Projects;
    private String _StringResult; // For simple string returns

    // Getters and Setters
    public String get_Status() {
        return _Status;
    }
    public void set_Status(String _Status) {
        this._Status = _Status;
    }

    public ProjectDto get_Project() {
        return _Project;
    }
    public void set_Project(ProjectDto _Project) {
        this._Project = _Project;
    }

    public List<ProjectDto> get_Projects() {
        return _Projects;
    }
    public void set_Projects(List<ProjectDto> _Projects) {
        this._Projects = _Projects;
    }

    public String get_StringResult() {
        return _StringResult;
    }
    public void set_StringResult(String _StringResult) {
        this._StringResult = _StringResult;
    }
}
package com.micro.cast.dto;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CastingDto {

    

    @JsonProperty("actorName")
    String _actorName;

    @JsonProperty("role")
    String _role;

    @JsonProperty("status")
    String _status;

    @JsonProperty("projectId")
    String _projectId; // to link casting with a project
    List<ProjectDto> projects;

    public CastingDto() {
    }

    public CastingDto( String actorName, String role, String status, String projectId) {
        
        this._actorName = actorName;
        this._role = role;
        this._status = status;
        this._projectId = projectId;
    }



    public String getActorName() {
        return _actorName;
    }

    public void setActorName(String _actorName) {
        this._actorName = _actorName;
    }

    public String getRole() {
        return _role;
    }

    public void setRole(String _role) {
        this._role = _role;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String _status) {
        this._status = _status;
    }

    public String getProjectId() {
        return _projectId;
    }

    public void setProjectId(String _projectId) {
        this._projectId = _projectId;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDto> projects) {
        this.projects = projects;
    }
}

package com.micro.project.irepository;
import java.util.List;

import com.micro.project.models.Project;

public interface IprojectRepo {
    public List<Project> GetAllProjects();
    Project GetProjectById(String ProjectId);
    Project GetProjectByTitle(String ProjectTitle);
    Project AddProject(Project Project);
    Integer GetProjectCount();
    String GetProjectTitleById(String ProjectId);
    String GetProjectIdByTitle(String ProjectTitle);


}

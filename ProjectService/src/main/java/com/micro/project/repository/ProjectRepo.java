package com.micro.project.repository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import  com.micro.project.irepository.IprojectRepo;
import com.micro.project.models.Project;

@Repository(value="ProjectRepo")
public class ProjectRepo implements IprojectRepo {

JdbcTemplate _JdbcTemplate;

@Autowired
ProjectRepo (JdbcTemplate jdbcTemplate){
this._JdbcTemplate=jdbcTemplate;
}
    @Override
    public List<Project> GetAllProjects(){

        List<Project> projects;
        String sql="select * from project";
        try {
            projects=_JdbcTemplate.query(sql, new ProjectRowMapper());
        } catch (Exception e) {
            System.out.println("Error fetching projects:" + e.getMessage());
            return null;
        }
return projects;
    }
      @Override
    public Project GetProjectById(String ProjectId) 
    {
        try
        {
            String query = "select * from project where project_id = ?";
            ProjectId = ProjectId.toLowerCase();
            return _JdbcTemplate.queryForObject(query, new ProjectRowMapper(), ProjectId);
        }
        catch (Exception e)
        {
            System.out.println("Error fetching Project by ID: " + e.getMessage());
            return null; // or throw a custom exception
        }
    }

    @Override
    public String GetProjectTitleById(String ProjectId) 
    {
        try
        {
            ProjectId = ProjectId.toLowerCase();
            return _JdbcTemplate.queryForObject("select project_title from project where project_id = ?", 
            String.class, ProjectId);
        }
        catch (Exception e)
        {
            System.out.println("Error fetching Project Title by ID: " + e.getMessage());
            return null; // or throw a custom exception
        }
    }

    @Override
    public String GetProjectIdByTitle(String ProjectTitle) 
    {
        try
        {
            ProjectTitle = ProjectTitle.toLowerCase();
            return _JdbcTemplate.queryForObject("select project_id from Project where project_title = ?", 
            String.class, ProjectTitle);
        }
        catch (Exception e)
        {
            System.out.println("Error fetching Project ID by Title: " + e.getMessage());
            return null; // or throw a custom exception
        }
    }

    @Override
    public Project GetProjectByTitle(String ProjectTitle) 
    {
        try
        {
            ProjectTitle = ProjectTitle.toLowerCase();
            String query = "select * from Project where project_title = ?";
            return _JdbcTemplate.queryForObject(query, new ProjectRowMapper(), ProjectTitle);
        }
        catch (Exception e)
        {
            System.out.println("Error fetching Project by Title: " + e.getMessage());
            return null; // or throw a custom exception
        }
    }

    @Override
    public Project AddProject(Project Project) 
    {
        try
        {
            String ProjectId = "P" + (GetProjectCount() + 100 + 1);
            Project.set_projectId(ProjectId);
            String sql = "INSERT INTO Project (project_id, project_title, type,status,episodes) VALUES (?, ?, ?,?,?)";
            _JdbcTemplate.update(sql, Project.get_projectId(), 
            Project.getProjecttitle(),  Project.getType(), Project.getStatus(),Project.getEpisodes());
            return Project;
        }
        catch (Exception e)
        {
            System.out.println("Error adding Project: " + e.getMessage());
            return null; // or throw a custom exception
        }
    }

    @Override
    public Integer GetProjectCount()
    {
        try
        {
            String sql = "SELECT COUNT(*) FROM Project";
            return _JdbcTemplate.queryForObject(sql, Integer.class);
        }
        catch (Exception e)
        {
            System.out.println("Error fetching Project count: " + e.getMessage());
            return 0;
        }
    }
}

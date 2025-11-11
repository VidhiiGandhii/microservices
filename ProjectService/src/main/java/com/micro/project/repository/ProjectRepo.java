package com.micro.project.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.micro.project.irepository.IprojectRepo;
import com.micro.project.models.Project;

@Repository(value = "ProjectRepo")
public class ProjectRepo implements IprojectRepo {

    JdbcTemplate _JdbcTemplate;

    @Autowired
    ProjectRepo(JdbcTemplate jdbcTemplate) {
        this._JdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Project> GetAllProjects() {
        // We remove the try-catch. If this fails, it will throw a
        // DataAccessException, which our controller will catch as a 500 error.
        String sql = "select * from project";
        return _JdbcTemplate.query(sql, new ProjectRowMapper());
    }

    @Override
    public Project GetProjectById(String ProjectId) {
        // We remove the try-catch. If no project is found,
        // this will correctly throw EmptyResultDataAccessException.
        String query = "select * from project where project_id = ?";
        return _JdbcTemplate.queryForObject(query, new ProjectRowMapper(), ProjectId.toLowerCase());
    }

    @Override
    public String GetProjectTitleById(String ProjectId) {
        // We remove the try-catch.
        return _JdbcTemplate.queryForObject("select project_title from project where project_id = ?",
                String.class, ProjectId.toLowerCase());
    }

    @Override
    public String GetProjectIdByTitle(String ProjectTitle) {
        // We remove the try-catch.
        return _JdbcTemplate.queryForObject("select project_id from Project where project_title = ?",
                String.class, ProjectTitle.toLowerCase());
    }

    @Override
    public Project GetProjectByTitle(String ProjectTitle) {
        // We remove the try-catch.
        String query = "select * from Project where project_title = ?";
        return _JdbcTemplate.queryForObject(query, new ProjectRowMapper(), ProjectTitle.toLowerCase());
    }

    @Override
    public Project AddProject(Project Project) {
        // We remove the try-catch. This will now correctly throw
        // DuplicateKeyException if a project title (if unique) already exists.
        
        // Note: This logic for generating IDs is risky in a multi-user environment
        // but we will leave it for now.
        String ProjectId = "P" + (GetProjectCount() + 100 + 1);
        Project.set_projectId(ProjectId);
        
        String sql = "INSERT INTO Project (project_id, project_title, type, status, episodes) VALUES (?, ?, ?, ?, ?)";
        _JdbcTemplate.update(sql, Project.get_projectId(),
                Project.getProjecttitle(), Project.getType(), Project.getStatus(), Project.getEpisodes());
        return Project;
    }

    @Override
    public Integer GetProjectCount() {
        // We remove the try-catch.
        String sql = "SELECT COUNT(*) FROM Project";
        return _JdbcTemplate.queryForObject(sql, Integer.class);
    }
}
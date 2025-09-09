package com.micro.project.repository;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.micro.project.models.Project;
public class ProjectRowMapper implements RowMapper<Project> {
    
    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {

        if(rs == null)
        {
            return null;
        }

        String projectId = rs.getString("project_id");
        String projectName = rs.getString("project_title");
        String projectType = rs.getString("type");
        String projectStatus= rs.getString("status");
        int episodes = rs.getInt("episodes");
        return new Project(projectId, projectName, projectType,projectStatus,episodes);
    }
}

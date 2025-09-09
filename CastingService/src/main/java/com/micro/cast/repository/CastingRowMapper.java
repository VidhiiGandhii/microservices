package com.micro.cast.repository;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

import com.micro.cast.models.Casting;

public class CastingRowMapper implements RowMapper<Casting> {

    @Override
    public Casting mapRow(ResultSet rs, int rowNum) throws SQLException {

        if (rs == null) {
            return null;
        }

        String castingId = rs.getString("casting_id");
        String actorName = rs.getString("actor_name");
        String role = rs.getString("role");
        String status = rs.getString("status");
        String projectId = rs.getString("project_id");

        return new Casting(castingId, actorName, role, status, projectId);
    }
}

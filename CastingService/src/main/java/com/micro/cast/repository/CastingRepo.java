package com.micro.cast.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


import org.springframework.stereotype.Repository;


import com.micro.cast.irepository.ICastingRepo;
import com.micro.cast.models.Casting;

@Repository
public class CastingRepo implements ICastingRepo {

    
    JdbcTemplate _JdbcTemplate;

@Autowired
CastingRepo (JdbcTemplate jdbcTemplate){
this._JdbcTemplate=jdbcTemplate;
}

    @Override
    public List<Casting> GetAllCastings() {
        String sql = "SELECT * FROM casting";
        return _JdbcTemplate.query(sql, new CastingRowMapper());
    }

    @Override
    public Casting GetCastingById(String castingId) {
        String sql = "SELECT * FROM casting WHERE casting_id = ?";
        return  _JdbcTemplate.queryForObject(sql, new CastingRowMapper(), castingId);
    }

    @Override
    public Casting GetCastingByActorName(String actorName) {
        String sql = "SELECT * FROM casting WHERE actor_name = ?";
        return _JdbcTemplate.queryForObject(sql, new CastingRowMapper(), actorName);
    }

    @Override
    public Casting AddCasting(Casting casting) {
        String sql = "INSERT INTO casting (casting_id, actor_name, role, project_id) VALUES (?, ?, ?, ?)";
         _JdbcTemplate.update(sql, casting.getCastingId(), casting.getActorName(), casting.getRole(), casting.getProjectId());
        return casting;
    }

    @Override
    public Integer GetCastingCount() {
        String sql = "SELECT COUNT(*) FROM casting";
        return  _JdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public String GetCastingIdByActorName(String actorName) {
        String sql = "SELECT casting_id FROM casting WHERE actor_name = ?";
        return  _JdbcTemplate.queryForObject(sql, String.class, actorName);
    }

    @Override
    public String GetActorNameByCastingId(String castingId) {
        String sql = "SELECT actor_name FROM casting WHERE casting_id = ?";
        return  _JdbcTemplate.queryForObject(sql, String.class, castingId);
    }

    // ✅ NEW: Get Actor Name by Id
    @Override
    public String GetActorNameById(String castingId) {
        String sql = "SELECT actor_name FROM casting WHERE casting_id = ?";
        return  _JdbcTemplate.queryForObject(sql, String.class, castingId);
    }

    // ✅ NEW: Get Castings by ProjectId
    @Override
    public List<Casting> GetCastingsByProjectId(String projectId) {
        String sql = "SELECT * FROM casting WHERE project_id = ?";
        return _JdbcTemplate.query(sql, new CastingRowMapper(), projectId);
    }
}

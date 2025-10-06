package com.micro.schedule.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.micro.schedule.irepo.IScheduleRepo;
import com.micro.schedule.model.Schedule;

@Repository
public class ScheduleRepo implements IScheduleRepo {

    @Autowired
     JdbcTemplate jdbcTemplate;

    @Override
    public Schedule save(Schedule schedule) {
        String sql = "INSERT INTO schedule (movie_name, location, date, time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, schedule.getMovieName(), schedule.getLocation(), schedule.getDate(), schedule.getTime());
        return schedule;
    }

    @Override
    public List<Schedule> findAll() {
        String sql = "SELECT * FROM schedule";
        return jdbcTemplate.query(sql, new ScheduleRowMapper());
    }

    @Override
    public Schedule findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE schedule_id = ?";
        return jdbcTemplate.queryForObject(sql, new ScheduleRowMapper(), id);
    }

    @Override
    public int update(Long id, Schedule schedule) {
        String sql = "UPDATE schedule SET movie_name=?, location=?, date=?, time=? WHERE schedule_id=?";
        return jdbcTemplate.update(sql, schedule.getMovieName(), schedule.getLocation(), schedule.getDate(), schedule.getTime(), id);
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM schedule WHERE schedule_id=?";
        return jdbcTemplate.update(sql, id);
    }
}

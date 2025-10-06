package com.micro.schedule.repo;

import com.micro.schedule.model.Schedule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleRowMapper implements RowMapper<Schedule> {
    @Override
    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(rs.getLong("schedule_id"));
        schedule.setMovieName(rs.getString("movie_name"));
        schedule.setLocation(rs.getString("location"));
        schedule.setDate(rs.getString("date"));
        schedule.setTime(rs.getString("time"));
        return schedule;
    }
}

package com.micro.schedule.service;

import com.micro.schedule.model.Schedule;
import com.micro.schedule.irepo.IScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
     IScheduleRepo repo;

    public Schedule addSchedule(Schedule schedule) {
        return repo.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return repo.findAll();
    }

    public Schedule getScheduleById(Long id) {
        return repo.findById(id);
    }

    public int updateSchedule(Long id, Schedule schedule) {
        return repo.update(id, schedule);
    }

    public int deleteSchedule(Long id) {
        return repo.delete(id);
    }
}

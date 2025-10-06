package com.micro.schedule.irepo;

import com.micro.schedule.model.Schedule;
import java.util.List;

public interface IScheduleRepo {
    Schedule save(Schedule schedule);
    List<Schedule> findAll();
    Schedule findById(Long id);
    int update(Long id, Schedule schedule);
    int delete(Long id);
}

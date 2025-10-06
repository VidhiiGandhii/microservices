package com.micro.schedule.controller;

import com.micro.schedule.model.Schedule;
import com.micro.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/add")
    public String addSchedule(@RequestBody Schedule schedule) {
        scheduleService.addSchedule(schedule);
        return "Schedule added successfully!";
    }

    @GetMapping("/all")
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    @PutMapping("/update/{id}")
    public String updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        int rows = scheduleService.updateSchedule(id, schedule);
        return rows > 0 ? "Schedule updated successfully!" : "Schedule not found!";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        int rows = scheduleService.deleteSchedule(id);
        return rows > 0 ? "Schedule deleted successfully!" : "Schedule not found!";
    }
}

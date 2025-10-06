package com.micro.schedule.dto;

public class ScheduleDto {
    private Long scheduleId;
    private String movieName;
    private String location;
    private String date;
    private String time;

    public ScheduleDto() {}

    public ScheduleDto(Long scheduleId, String movieName, String location, String date, String time) {
        this.scheduleId = scheduleId;
        this.movieName = movieName;
        this.location = location;
        this.date = date;
        this.time = time;
    }

    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}

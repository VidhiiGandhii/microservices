package com.micro.schedule.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
    
@RequestMapping("/schedule")
@RestController
public class ScheduleController 
{

        /*
     * http://localhost:4005/schedule/
     */
    @GetMapping(path = "/allschedules")
    public String getAllSchedules() 
    {
        System.out.println("Hello from schedule controller!");
        
        return "Hello from schedule controller!";
    }

 @GetMapping(path = "/schedule/{schedulename}")
    public String getTeamByName(@PathVariable("schedulename") String schedulename) 
    {
        System.out.println("Hi!! " + "welcome " + schedulename + " to the Production!");
       
        return "Hi!! " + "welcome " + schedulename + " to the Production!";
    }
   @GetMapping(path = "/schedule")
    public String getTeamByNameFromRequest(@RequestParam("projectname") String schedulename)
    {
        System.out.println("Hi!! " + "welcome " + schedulename
         + " to the Production!");
       
        return "Hi!! " + "welcome " + schedulename+ " to the Production!";
    }

}

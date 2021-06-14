package com.udacity.jdnd.course3.critter.Controllers;

import com.udacity.jdnd.course3.critter.DTOs.ScheduleDTO;
import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Entities.Schedule;
import com.udacity.jdnd.course3.critter.Service.EmployeeService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import com.udacity.jdnd.course3.critter.Service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;
    private ScheduleDTO transferScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Pet> petList = schedule.getPets();
        List<Employee> employeeList = schedule.getEmployees();

        List<Long> petIds = new ArrayList<>();
        if(petList.size() != 0){
            for(Pet p : petList){
                petIds.add(p.getId());
            }
        }

        List<Long> employeeIds = new ArrayList<>();
        if(employeeList.size() != 0){
            for(Employee e : employeeList){
                employeeIds.add(e.getId());
            }
        }
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }

    private Schedule transferScheduledtoToSchedule(ScheduleDTO scheduleDTO){

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> petIds = scheduleDTO.getPetIds();
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();

        List<Pet> petList = new ArrayList<>();
        if(petIds.size() != 0){
            for(Long id : petIds){
                Pet pet = petService.getPet(id);
                petList.add(pet);
            }
        }

        List<Employee> employeeList = new ArrayList<>();
        if(petIds.size() != 0){
            for(Long id : employeeIds){
                Employee employee = employeeService.getEmployee(id);
                employeeList.add(employee);
            }
        }

        schedule.setEmployees(employeeList);
        schedule.setPets(petList);

        return schedule;
    }
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule newSchedule = transferScheduledtoToSchedule(scheduleDTO);
        Schedule schedule = scheduleService.save(newSchedule);
        return transferScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> results = new ArrayList<ScheduleDTO>();
        List<Schedule> scheduleList = scheduleService.getAllSchedules();

        for(Schedule s : scheduleList) {
            results.add(transferScheduleToScheduleDTO(s));
        }

        return results;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> scheduleList = scheduleService.getScheduleByPetId(petId);
        List<ScheduleDTO> results = new ArrayList<>();

        if(scheduleList.size() != 0){
            for(Schedule s : scheduleList){
                results.add(transferScheduleToScheduleDTO(s));
            }
        }

        return results;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> scheduleList = scheduleService.getScheduleByEmployeeId(employeeId);
        List<ScheduleDTO> results = new ArrayList<>();

        if(scheduleList.size() != 0){
            for(Schedule s : scheduleList){
                results.add(transferScheduleToScheduleDTO(s));
            }
        }

        return results;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> scheduleList = scheduleService.getSchedulesForCustomer(customerId);
        List<ScheduleDTO> results = new ArrayList<>();

        for(Schedule s : scheduleList){
            results.add(transferScheduleToScheduleDTO(s));
        }
        return results;
    }
}

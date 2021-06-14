package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Entities.Schedule;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CustomerRepository customerRepository;
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
    public List<Schedule> getScheduleByPetId(long petId) {
        return scheduleRepository.findAllByPets_Id(petId);
    }

    public List<Schedule> getScheduleByEmployeeId(long employeeId) {
        return scheduleRepository.findAllByEmployees_Id(employeeId);
    }
    public List<Schedule> getSchedulesForCustomer(long customerId){
        Customer customer = customerRepository.findById(customerId).orElse(null);
        List<Pet> pets = Objects.requireNonNull(customer).getPets();
        ArrayList<Schedule> schedules = new ArrayList<>();
        for (Pet pet : pets) {
            schedules.addAll(scheduleRepository.findAllByPets_Id(pet.getId()));
        }
        return schedules;
    }
}

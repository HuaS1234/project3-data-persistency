package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.EmployeeSkill;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;


@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    public Employee getEmployee(long employeeId) {
        Optional<Employee> searchResult = employeeRepository.findById(employeeId);
        if(searchResult.isPresent()) {
            return searchResult.get();
        }else {
            throw new NoSuchElementException("Employee with id:" + employeeId + " can not be found");
        }
    }
    public void setAvailability(Set<DayOfWeek> availableDays, long employeeId) {
        Optional<Employee> searchResult = employeeRepository.findById(employeeId);

        if(searchResult.isPresent()) {
            searchResult.get().setDaysAvailable(availableDays);
            employeeRepository.save(searchResult.get());
        }else {
            throw new NoSuchElementException("Employee with id:" + employeeId + " can not be found");
        }

    }
    public List<Employee> getEmployeesForService(Set<EmployeeSkill> skills, DayOfWeek availableDays) {
        List<Employee> result = new ArrayList<Employee>();
        List<Employee> availableEmployees = new ArrayList<Employee>();

        availableEmployees = employeeRepository.findAllByDaysAvailable(availableDays);

        for(Employee e : availableEmployees) {
            if(e.getSkills().containsAll(skills)) {
                result.add(e);
            }
        }
        return result;
    }
}

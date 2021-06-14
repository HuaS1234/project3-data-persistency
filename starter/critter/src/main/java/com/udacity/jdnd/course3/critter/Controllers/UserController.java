package com.udacity.jdnd.course3.critter.Controllers;

import com.udacity.jdnd.course3.critter.DTOs.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTOs.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTOs.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Employee;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private PetService petService;
    @Autowired
    private EmployeeService employeeService;

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        List<Long> petIds = customerDTO.getPetIds();

        List<Pet> pets = new ArrayList<>();
        if(petIds != null && petIds.size() != 0){
            for(Long id : petIds){
                pets.add(petService.getPet(id));
            }
        }
        customer.setPets(pets);
        return customer;
    }
    public CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Pet> pets = customer.getPets();

        List<Long> petIds = new ArrayList<>();
        if(petIds != null && pets.size() != 0){
            for(Pet p : pets){
                Long id = p.getId();
                petIds.add(id);
            }
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }
    public EmployeeDTO convertEmployeeToEmployeeDTO (Employee employee){
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        return dto;
    }

    public Employee convertEmployeeDTOToEmployee (EmployeeDTO dto){
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        return employee;
    }
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerService.save(convertCustomerDTOToCustomer(customerDTO));
        return convertCustomerToCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customerList = customerService.getAllCustomers();
        List<CustomerDTO> customerListDTO = new ArrayList<CustomerDTO>();

        for(Customer c : customerList) {
            customerListDTO.add(convertCustomerToCustomerDTO(c));
        }

        return customerListDTO;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPet(petId);
        Customer customer = pet.getOwner();
        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee newEmployee = convertEmployeeDTOToEmployee(employeeDTO);
        Employee employee = employeeService.save(newEmployee);
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEmployeeToEmployeeDTO(employeeService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeeList = employeeService.getEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        List<EmployeeDTO> results = new ArrayList<EmployeeDTO>();

        for(Employee e : employeeList) {
            results.add(convertEmployeeToEmployeeDTO(e));
        }

        return results;
    }

}

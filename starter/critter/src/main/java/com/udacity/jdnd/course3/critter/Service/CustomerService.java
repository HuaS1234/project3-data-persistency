package com.udacity.jdnd.course3.critter.Service;


import com.udacity.jdnd.course3.critter.DTOs.CustomerDTO;
import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(long customerId) {
        Optional<Customer> searchResult = customerRepository.findById(customerId);
        if(searchResult.isPresent()) {
            return searchResult.get();
        }else {
            throw new NoSuchElementException("Employee with id:" + customerId + " can not be found");
        }

    }
//    public Customer getOne(Long id) {
//        return customerRepository.getOne(id);
//    }
//
//    public Customer getCustomerByPetId(Long petId) {
//        return petRepository.getOne(petId).getCustomer();
//    }
//
//    public List<Customer> getAll() {
//        return customerRepository.findAll();
//    }
}
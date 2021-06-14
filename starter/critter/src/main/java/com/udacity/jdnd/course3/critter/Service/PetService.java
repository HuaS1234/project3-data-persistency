package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;
    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet getPet(Long id) {
        Optional<Pet> searchResult = petRepository.findById(id);
        if(searchResult.isPresent()) {
            return searchResult.get();
        }else {
            throw new NoSuchElementException("Pet with id:" + id + " can not be found");
        }
    }

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }
    public List<Pet> getPetsByOwnerId(long ownerId) {
        return petRepository.findAllPetsByCustomerId(ownerId);
    }
}

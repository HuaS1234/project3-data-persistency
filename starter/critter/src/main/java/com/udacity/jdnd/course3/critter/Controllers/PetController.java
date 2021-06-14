package com.udacity.jdnd.course3.critter.Controllers;

import com.udacity.jdnd.course3.critter.DTOs.PetDTO;
import com.udacity.jdnd.course3.critter.Entities.Customer;
import com.udacity.jdnd.course3.critter.Entities.Pet;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;
    @Autowired
    CustomerService customerService;

    private Pet convertPetDtoToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if(pet.getOwner() != null){
            petDTO.setOwnerId(pet.getOwner().getId());
        }
        return petDTO;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDtoToPet(petDTO);

        if((Long) petDTO.getOwnerId() != 0) {
            Customer owner = customerService.getCustomer(petDTO.getOwnerId());
            pet.setOwner(owner);
            owner.addPet(pet);
        }

        pet = petService.savePet(pet);
        return convertPetToPetDTO(pet);

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> results = new ArrayList<PetDTO>();
        List<Pet> petList = petService.getPetsByOwnerId(ownerId);

        for(Pet p : petList) {
            results.add(convertPetToPetDTO(p));
        }

        return results;
    }
}

package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.owner.id = :ownerId")
    List<Pet> findAllPetsByCustomerId(@Param("ownerId") long ownerId);
}

package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByPets_Id(long id);
    List<Schedule> findAllByEmployees_Id(long id);
}

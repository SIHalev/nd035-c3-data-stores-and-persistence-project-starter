package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where :petId member of s.petIds")
    List<Schedule> findAllSchedulesByPet(Long petId);

    @Query("select s from Schedule s where :employeeId member of s.employeeIds")
    List<Schedule> findAllSchedulesByEmployee(Long employeeId);

    // @Query("select s from Schedule s where (select p.id from Pet p where p.owner.id = :customerId) member of s.petIds")
    // List<Schedule> findAllSchedulesByCustomer(Long customerId);
    List<Schedule> findAllByPetIdsIn(List<Long> petIds);

}

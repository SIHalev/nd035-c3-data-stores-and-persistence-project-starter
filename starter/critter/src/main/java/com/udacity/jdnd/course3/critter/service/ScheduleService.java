package com.udacity.jdnd.course3.critter.service;

import com.google.common.collect.Lists;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final PetRepository petRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petRepository = petRepository;
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return Lists.newArrayList(scheduleRepository.findAll());
    }


    public List<Schedule> getAllSchedulesForPet(long petId) {
        return scheduleRepository.findAllSchedulesByPet(petId);
    }

    public List<Schedule> getAllSchedulesForEmployee(long employeeId) {
        return scheduleRepository.findAllSchedulesByEmployee(employeeId);
    }

    public List<Schedule> getAllSchedulesForCustomer(long customerId) {
        final List<Long> allOwnedPetIds = petRepository.findAllByOwnerId(customerId).stream()
                .map(Pet::getId)
                .collect(Collectors.toList());
        return scheduleRepository.findAllByPetIdsIn(allOwnedPetIds);
    }
}

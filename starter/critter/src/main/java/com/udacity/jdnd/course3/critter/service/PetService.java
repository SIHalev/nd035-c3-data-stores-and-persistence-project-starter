package com.udacity.jdnd.course3.critter.service;

import com.google.common.collect.Lists;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Transactional
    public Pet addPet(Pet pet) {
        Customer owner = pet.getOwner();
        if (owner.getPets() != null) {
            owner.getPets().add(pet);
        } else {
            owner.setPets(Collections.singletonList(pet));
        }

        return petRepository.save(pet);
    }

    public Optional<Pet> getPet(Long id) {
        return petRepository.findById(id);
    }

    public List<Pet> getOwnerPets(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }

    public List<Pet> getAllPets() {
        return Lists.newArrayList(petRepository.findAll());
    }

}

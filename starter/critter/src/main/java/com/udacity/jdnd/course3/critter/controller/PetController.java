package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.dto.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final ModelMapper modelMapper;

    @Autowired
    public PetController(PetService petService,
            ModelMapper modelMapper) {
        this.petService = petService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        final Pet pet = petService.addPet(convertPetDTO(petDTO));
        return convertPet(pet);
    }

    @PostMapping("/{petId}") // Not sure why the postman collections wants the user also to put petId
    public PetDTO savePet(@PathVariable long petId, @RequestBody PetDTO petDTO) {
        return savePet(petDTO);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return petService.getPet(petId)
                .map(this::convertPet)
                .orElse(null);
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return petService.getAllPets().stream()
                .map(this::convertPet)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getOwnerPets(ownerId).stream()
                .map(this::convertPet)
                .collect(Collectors.toList());
    }

    private Pet convertPetDTO(PetDTO petDTO) {
        return modelMapper.map(petDTO, Pet.class);
    }

    private PetDTO convertPet(Pet pet) {
        return modelMapper.map(pet, PetDTO.class);
    }
}

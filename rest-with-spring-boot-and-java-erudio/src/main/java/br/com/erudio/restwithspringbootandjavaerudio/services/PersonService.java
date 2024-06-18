package br.com.erudio.restwithspringbootandjavaerudio.services;

import br.com.erudio.restwithspringbootandjavaerudio.controllers.PersonController;
import br.com.erudio.restwithspringbootandjavaerudio.mapper.ModelMapper;
import br.com.erudio.restwithspringbootandjavaerudio.model.dto.PersonDto;
import br.com.erudio.restwithspringbootandjavaerudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbootandjavaerudio.model.entities.Person;
import br.com.erudio.restwithspringbootandjavaerudio.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public PersonDto findById(Long id){
        log.info("Finding one person!");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        PersonDto dto = ModelMapper.parseObject(entity, PersonDto.class);
        // HATEOES
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return dto;
    }

    public List<PersonDto> findAll(){
        log.info("Finding all persons!");
        List<PersonDto> persons = ModelMapper.parseListObjects(personRepository.findAll(),PersonDto.class);
        persons.stream()
                .forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }

    public PersonDto create(PersonDto person){
        log.info("Creating one person!");
        Person entity = ModelMapper.parseObject(person, Person.class);
        PersonDto dto = ModelMapper.parseObject(personRepository.save(entity), PersonDto.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public PersonDto update(PersonDto person){
        log.info("Updating one person!");
        Person entity = personRepository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        PersonDto dto = ModelMapper.parseObject(personRepository.save(entity), PersonDto.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id){
        log.info("Deleting one person!");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(entity);
    }
}

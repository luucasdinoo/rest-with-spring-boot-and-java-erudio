package br.com.erudio.restwithspringbootandjavaerudio.services;

import br.com.erudio.restwithspringbootandjavaerudio.mapper.PersonMapper;
import br.com.erudio.restwithspringbootandjavaerudio.model.dto.PersonDto;
import br.com.erudio.restwithspringbootandjavaerudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbootandjavaerudio.model.entities.Person;
import br.com.erudio.restwithspringbootandjavaerudio.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public PersonDto findById(Long id){
        log.info("Finding one person!");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return PersonMapper.parseObject(entity, PersonDto.class);
    }

    public List<PersonDto> findAll(){
        log.info("Finding all persons!");
        return PersonMapper.parseListObjects(personRepository.findAll(),PersonDto.class);
    }

    public PersonDto create(PersonDto person){
        log.info("Creating one person!");
        Person entity = PersonMapper.parseObject(person, Person.class);
        return PersonMapper.parseObject(personRepository.save(entity), PersonDto.class);
    }

    public PersonDto update(PersonDto person){
        log.info("Updating one person!");
        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return PersonMapper.parseObject(personRepository.save(entity), PersonDto.class);
    }

    public void delete(Long id){
        log.info("Deleting one person!");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(entity);
    }
}

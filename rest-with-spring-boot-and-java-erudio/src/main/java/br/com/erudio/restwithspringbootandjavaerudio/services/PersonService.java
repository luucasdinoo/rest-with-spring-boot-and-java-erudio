package br.com.erudio.restwithspringbootandjavaerudio.services;

import br.com.erudio.restwithspringbootandjavaerudio.data.dto.PersonDto;
import br.com.erudio.restwithspringbootandjavaerudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbootandjavaerudio.mapper.DozerMapper;
import br.com.erudio.restwithspringbootandjavaerudio.model.Person;
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
        return DozerMapper.parseObject(entity, PersonDto.class);
    }

    public List<PersonDto> findAll(){
        log.info("Finding all persons!");
        return DozerMapper.parseListObject(personRepository.findAll(),PersonDto.class);
    }

    public PersonDto create(PersonDto person){
        log.info("Creating one person!");
        Person entity = DozerMapper.parseObject(person, Person.class);
        return DozerMapper.parseObject(personRepository.save(entity), PersonDto.class);
    }

    public PersonDto update(PersonDto person){
        log.info("Updating one person!");
        Person entity = personRepository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return DozerMapper.parseObject(personRepository.save(entity), PersonDto.class);
    }

    public void delete(Long id){
        log.info("Deleting one person!");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(entity);
    }
}

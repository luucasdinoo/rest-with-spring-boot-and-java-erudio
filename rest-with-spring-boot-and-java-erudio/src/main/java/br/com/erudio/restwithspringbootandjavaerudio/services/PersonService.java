package br.com.erudio.restwithspringbootandjavaerudio.services;

import br.com.erudio.restwithspringbootandjavaerudio.controllers.PersonController;
import br.com.erudio.restwithspringbootandjavaerudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.restwithspringbootandjavaerudio.mapper.ModelMapper;
import br.com.erudio.restwithspringbootandjavaerudio.model.dto.PersonDto;
import br.com.erudio.restwithspringbootandjavaerudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbootandjavaerudio.model.entities.Person;
import br.com.erudio.restwithspringbootandjavaerudio.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    private final PagedResourcesAssembler<PersonDto> assembler;

    public PersonDto findById(Long id){
        log.info("Finding one person!");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        PersonDto dto = ModelMapper.parseObject(entity, PersonDto.class);
        // HATEOES
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return dto;
    }

    public PagedModel<EntityModel<PersonDto>> findAll(Pageable pageable){
        log.info("Finding all persons!");

        var personPage = personRepository.findAll(pageable);
        var personDtoPage = personPage.map(p -> ModelMapper.parseObject(p, PersonDto.class));
        personDtoPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(),"asc")).withSelfRel();
        return assembler.toModel(personDtoPage, link);
    }

    public PagedModel<EntityModel<PersonDto>> findPersonsByName(String firstname, Pageable pageable){
        log.info("Finding all persons!");

        var personPage = personRepository.findPersonsByName(firstname, pageable);
        var personDtoPage = personPage.map(p -> ModelMapper.parseObject(p, PersonDto.class));
        personDtoPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(),"asc")).withSelfRel();
        return assembler.toModel(personDtoPage, link);
    }

    public PersonDto create(PersonDto person){
        if (person == null) throw new RequiredObjectIsNullException();
        log.info("Creating one person!");
        Person entity = ModelMapper.parseObject(person, Person.class);
        PersonDto dto = ModelMapper.parseObject(personRepository.save(entity), PersonDto.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public PersonDto update(PersonDto person){
        if (person == null) throw new RequiredObjectIsNullException();
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

    @Transactional
    public PersonDto disablePerson(Long id){
        log.info("Disabling one person!");
        personRepository.disablePerson(id);
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        PersonDto dto = ModelMapper.parseObject(entity, PersonDto.class);
        // HATEOES
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return dto;
    }

    public void delete(Long id){
        log.info("Deleting one person!");
        Person entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        personRepository.delete(entity);
    }
}

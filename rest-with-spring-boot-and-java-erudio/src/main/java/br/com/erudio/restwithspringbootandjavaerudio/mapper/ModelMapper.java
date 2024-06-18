package br.com.erudio.restwithspringbootandjavaerudio.mapper;

import br.com.erudio.restwithspringbootandjavaerudio.model.dto.PersonDto;
import br.com.erudio.restwithspringbootandjavaerudio.model.entities.Person;

import java.util.ArrayList;
import java.util.List;

public class ModelMapper {

    private static final org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();

    // Mapear id de Person para key de PersonDto e vice versa
    static {
        mapper.createTypeMap(Person.class, PersonDto.class).addMapping(Person::getId, PersonDto::setKey);
        mapper.createTypeMap(PersonDto.class, Person.class).addMapping(PersonDto::getKey, Person::setId);
    }

    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        for (O o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }

}

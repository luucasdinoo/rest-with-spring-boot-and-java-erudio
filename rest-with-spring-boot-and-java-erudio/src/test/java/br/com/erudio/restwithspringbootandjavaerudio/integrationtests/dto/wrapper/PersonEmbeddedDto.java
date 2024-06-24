package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.wrapper;

import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities.PersonDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class PersonEmbeddedDto implements Serializable {

    @JsonProperty("personDtoList")
    private List<PersonDto> persons;
}

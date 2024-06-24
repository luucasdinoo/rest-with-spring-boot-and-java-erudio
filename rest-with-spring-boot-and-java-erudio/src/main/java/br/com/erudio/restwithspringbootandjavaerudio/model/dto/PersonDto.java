package br.com.erudio.restwithspringbootandjavaerudio.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"id","firstName","lastName", "address", "gender", "enabled"})
public class PersonDto extends RepresentationModel<PersonDto> implements Serializable {

    @JsonProperty("id")
    private Long key;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private Boolean enabled;

}
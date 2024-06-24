package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PersonDto implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private Boolean enabled;

}
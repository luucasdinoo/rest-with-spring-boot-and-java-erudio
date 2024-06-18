package br.com.erudio.restwithspringbootandjavaerudio.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class PersonDto implements Serializable {


    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}
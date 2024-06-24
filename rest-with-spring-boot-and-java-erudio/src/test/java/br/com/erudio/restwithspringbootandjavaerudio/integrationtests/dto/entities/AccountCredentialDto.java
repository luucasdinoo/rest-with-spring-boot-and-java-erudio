package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountCredentialDto implements Serializable {

    private String username;
    private String password;

}

package br.com.erudio.restwithspringbootandjavaerudio.model.dto.security;

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

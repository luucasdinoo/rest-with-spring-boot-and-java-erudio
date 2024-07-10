package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement
public class AccountCredentialDto implements Serializable {

    private String username;
    private String password;

}

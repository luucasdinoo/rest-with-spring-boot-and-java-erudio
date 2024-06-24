package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
public class TokenDto implements Serializable {

    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
    private String refreshToken;

}

package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Data
public class BookDto implements Serializable {

    private Long id;
    private String author;
    private Date launchDate;
    private Double price;
    private String title;

}

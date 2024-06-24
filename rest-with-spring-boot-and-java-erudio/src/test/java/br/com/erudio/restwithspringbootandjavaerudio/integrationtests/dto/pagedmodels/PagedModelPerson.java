package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.pagedmodels;

import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities.PersonDto;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
@NoArgsConstructor
@Data
public class PagedModelPerson implements Serializable {

    @XmlElement(name = "content")
    private List<PersonDto> content;
}

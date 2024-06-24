package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.pagedmodels;

import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities.BookDto;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class PagedModelBook implements Serializable {

    @XmlElement(name = "content")
    private List<BookDto> content;
}

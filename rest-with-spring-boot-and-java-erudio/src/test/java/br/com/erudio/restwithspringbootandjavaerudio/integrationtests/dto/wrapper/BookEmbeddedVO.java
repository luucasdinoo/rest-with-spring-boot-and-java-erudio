package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.wrapper;

import java.io.Serializable;
import java.util.List;

import br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.entities.BookDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BookEmbeddedVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("bookDtoList")
    private List<BookDto> books;

}
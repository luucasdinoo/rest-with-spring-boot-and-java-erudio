package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.wrapper;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WrapperBookDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JsonProperty("_embedded")
    private BookEmbeddedVO embedded;

}
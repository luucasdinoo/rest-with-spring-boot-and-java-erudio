package br.com.erudio.restwithspringbootandjavaerudio.integrationtests.dto.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@XmlRootElement
@NoArgsConstructor
@Data
public class WrapperPersonDto implements Serializable {

    @JsonProperty("_embedded")
    private PersonEmbeddedDto embedded;
}

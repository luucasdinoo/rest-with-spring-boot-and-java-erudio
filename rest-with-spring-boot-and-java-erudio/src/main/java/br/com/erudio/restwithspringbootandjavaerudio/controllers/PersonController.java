package br.com.erudio.restwithspringbootandjavaerudio.controllers;

import br.com.erudio.restwithspringbootandjavaerudio.model.dto.PersonDto;
import br.com.erudio.restwithspringbootandjavaerudio.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.erudio.restwithspringbootandjavaerudio.util.MediaType;

//@CrossOrigin
@RestController
@RequestMapping("/api/person/v1")
@RequiredArgsConstructor
@Tag(name = "People", description = "Endpoints for managing people")
public class PersonController {

    private final PersonService personService;

    @CrossOrigin(origins = "http://localhost:8080") // Permitir o acesso apenas para a url localhost:8080 com CORS
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds a person", description = "Finds a person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = PersonDto.class))),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public PersonDto findById(@PathVariable(value = "id") Long id) {
        return personService.findById(id);
    }

        @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds all people", description = "Finds all people",
            tags = {"People"},
            responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = {@Content(
                                mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = PersonDto.class)))
                }),
                @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<PagedModel<EntityModel<PersonDto>>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                     @RequestParam(value = "direction", defaultValue = "asc") String direction){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(personService.findAll(pageable));
    }

    @GetMapping(value = "findPersonByName/{firstname}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds people by name", description = "Finds people by name",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {@Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDto.class)))
                            }),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<PagedModel<EntityModel<PersonDto>>> findPersonsByName(@PathVariable(value = "firstname") String firstname,
                                                                                @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                                @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                                @RequestParam(value = "direction", defaultValue = "asc") String direction){

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(personService.findPersonsByName(firstname, pageable));
    }

    @CrossOrigin(origins = {"http://localhost:8080","https://erudio.com.br"})
    @PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Add a new person", description = "Add a new person by passing a JSON, XML  or YAML representation  of the person !",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = PersonDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public PersonDto create(@RequestBody PersonDto person) {
        return personService.create(person);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updating a person", description = "Updating a person by passing a JSON, XML  or YAML representation  of the person !",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = PersonDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public PersonDto update(@RequestBody PersonDto person) {
        return personService.update(person);
    }

    @PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Disable a person by your id", description = "Disable a person by your id",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = PersonDto.class))),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public PersonDto disablePerson(@PathVariable(value = "id") Long id) {
        return personService.disablePerson(id);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a person", description = "Deletes a person!",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

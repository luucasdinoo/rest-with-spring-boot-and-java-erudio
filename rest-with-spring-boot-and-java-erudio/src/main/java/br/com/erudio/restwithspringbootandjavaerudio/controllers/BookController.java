package br.com.erudio.restwithspringbootandjavaerudio.controllers;

import br.com.erudio.restwithspringbootandjavaerudio.model.dto.BookDto;
import br.com.erudio.restwithspringbootandjavaerudio.model.dto.PersonDto;
import br.com.erudio.restwithspringbootandjavaerudio.services.BookService;
import br.com.erudio.restwithspringbootandjavaerudio.util.MediaType;
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

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
@RequiredArgsConstructor
@Tag(name = "Book", description = "Endpoints for managing book")
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds a book", description = "Finds a book",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = BookDto.class))),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public BookDto findById(@PathVariable(value = "id") Long id) {
        return bookService.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds all books", description = "Finds all books",
            tags = {"Book"},
            responses = {
                @ApiResponse(description = "Success", responseCode = "200",
                        content = {@Content(
                                mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = BookDto.class)))
                }),
                @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<PagedModel<EntityModel<BookDto>>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                    @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                    @RequestParam(name = "direction", defaultValue = "0") String direction) {

        var sorDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sorDirection, "author"));
        return ResponseEntity.ok(bookService.findAll(pageable));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Add a new book", description = "Add a new book by passing a JSON, XML  or YAML representation  of the book !",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = BookDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public BookDto create(@RequestBody BookDto book) {
        return bookService.create(book);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updating a book", description = "Updating a book by passing a JSON, XML  or YAML representation  of the book !",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = BookDto.class))),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public BookDto update(@RequestBody BookDto book) {
        return bookService.update(book);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a book", description = "Deletes a book!",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

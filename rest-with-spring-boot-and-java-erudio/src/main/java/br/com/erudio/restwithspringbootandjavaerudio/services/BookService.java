package br.com.erudio.restwithspringbootandjavaerudio.services;

import br.com.erudio.restwithspringbootandjavaerudio.controllers.BookController;
import br.com.erudio.restwithspringbootandjavaerudio.controllers.PersonController;
import br.com.erudio.restwithspringbootandjavaerudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.restwithspringbootandjavaerudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbootandjavaerudio.mapper.ModelMapper;
import br.com.erudio.restwithspringbootandjavaerudio.model.dto.BookDto;
import br.com.erudio.restwithspringbootandjavaerudio.model.dto.PersonDto;
import br.com.erudio.restwithspringbootandjavaerudio.model.entities.Book;
import br.com.erudio.restwithspringbootandjavaerudio.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final PagedResourcesAssembler<BookDto> assembler;


    public BookDto findById(Long id){
        log.info("Finding one book!");
        Book entity = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        BookDto dto = ModelMapper.parseObject(entity, BookDto.class);
        // HATEOES
        dto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return dto;
    }

    public PagedModel<EntityModel<BookDto>> findAll(Pageable pageable){
        log.info("Finding all books!");

        var bookPage = bookRepository.findAll(pageable);
        var bookDtoPage = bookPage.map(b -> ModelMapper.parseObject(b, BookDto.class));
        bookDtoPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
        return assembler.toModel(bookDtoPage, link);
    }

    public BookDto create(BookDto book){
        if (book == null) throw new RequiredObjectIsNullException();
        log.info("Creating one book!");
        Book entity = ModelMapper.parseObject(book, Book.class);
        BookDto dto = ModelMapper.parseObject(bookRepository.save(entity), BookDto.class);
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public BookDto update(BookDto book){
        if (book == null) throw new RequiredObjectIsNullException();
        log.info("Updating one book!");
        Book entity = bookRepository.findById(book.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());
        BookDto dto = ModelMapper.parseObject(bookRepository.save(entity), BookDto.class);
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id){
        log.info("Deleting one book!");
        Book entity = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        bookRepository.delete(entity);
    }
}

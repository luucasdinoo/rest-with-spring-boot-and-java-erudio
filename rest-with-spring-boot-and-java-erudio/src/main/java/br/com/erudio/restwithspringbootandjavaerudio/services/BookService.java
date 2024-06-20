package br.com.erudio.restwithspringbootandjavaerudio.services;

import br.com.erudio.restwithspringbootandjavaerudio.controllers.BookController;
import br.com.erudio.restwithspringbootandjavaerudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.restwithspringbootandjavaerudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbootandjavaerudio.mapper.ModelMapper;
import br.com.erudio.restwithspringbootandjavaerudio.model.dto.BookDto;
import br.com.erudio.restwithspringbootandjavaerudio.model.entities.Book;
import br.com.erudio.restwithspringbootandjavaerudio.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public BookDto findById(Long id){
        log.info("Finding one book!");
        Book entity = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        BookDto dto = ModelMapper.parseObject(entity, BookDto.class);
        // HATEOES
        dto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return dto;
    }

    public List<BookDto> findAll(){
        log.info("Finding all books!");
        List<BookDto> books = ModelMapper.parseListObjects(bookRepository.findAll(),BookDto.class);
        books.stream()
                .forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return books;
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

package com.library.Library.controller;

import com.library.Library.dto.BookDto;
import com.library.Library.dto.RatingDto;
import com.library.Library.dto.create.CreateBookRequest;
import com.library.Library.exception.NotFoundException;
import com.library.Library.service.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "basicAuth")
public class BookController {

    private final BookService bookService;

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BookDto>> searchBooks(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                     @RequestParam(value = "limit", defaultValue = "20") Integer limit,
                                                     @RequestParam(value = "title", required = false) String title,
                                                     @RequestParam(value = "isbn", required = false) String isbn,
                                                     @RequestParam(value = "sortBy", required = false) String sortField) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, sortField));
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.search(pageable, title, isbn));
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BookDto>> getAllBooks(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                     @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "title"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.getAllBooks(pageable));
    }

    @RequestMapping(value = "/getBook", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> getOneBook(@RequestParam(value = "id") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.getOne(id));
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> create(@RequestBody CreateBookRequest newBook) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.addBook(newBook));
    }

    @RequestMapping(value = "/updateBook", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> update(@RequestBody CreateBookRequest updatedBook,
                                          @RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.updateBook(updatedBook, id));
    }

    @RequestMapping(value = "/deleteBook/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<BookDto> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookService.deleteBook(id));
    }

}

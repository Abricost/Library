package com.library.Library.controller;

import com.library.Library.dto.BookDto;
import com.library.Library.dto.BookOfferDto;
import com.library.Library.dto.create.CreateBookOfferRequest;
import com.library.Library.dto.create.UpdateBookOfferDto;
import com.library.Library.exception.NotFoundException;
import com.library.Library.mapper.BookOfferMapper;
import com.library.Library.repository.BookOfferRepository;
import com.library.Library.service.BookOfferService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/bookOffer")
@Slf4j
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class BookOfferController {
    private final BookOfferService bookOfferService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BookOfferDto>> getAllBookOffers(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                               @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "title"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookOfferService.listAll(pageable));
    }

    @RequestMapping(value = "/getBookOfferByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<BookOfferDto>> getBookOfferByUser(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                                 @RequestParam(value = "limit", defaultValue = "20") Integer limit) throws NotFoundException {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "title"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookOfferService.getBookOfferByUser(pageable));
    }

    @RequestMapping(value = "/addBookOffer", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookOfferDto> create(@RequestBody CreateBookOfferRequest newBookOffer) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookOfferService.addBookOffer(newBookOffer));
    }

    @RequestMapping(value = "/updateBookOffer", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookOfferDto> update(@RequestBody UpdateBookOfferDto updatedBookOffer,
                                               @RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(bookOfferService.updateBookOffer(updatedBookOffer, id));
    }

    @RequestMapping(value = "/deleteBookOffer/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
        bookOfferService.deleteBookOffer(id);
    }

}

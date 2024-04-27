package com.library.Library.controller;

import com.library.Library.dto.RatingDto;
import com.library.Library.dto.create.CreateRatingRequest;
import com.library.Library.exception.NotFoundException;
import com.library.Library.service.RatingService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "basicAuth")
public class RatingController {
    private final RatingService ratingService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RatingDto>> getAllRatings(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                         @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "rating"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.listAll(pageable));
    }


    @RequestMapping(value = "/getRating", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDto> getOneRating(@RequestParam(value = "id") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.getOne(id));
    }

    @RequestMapping(value = "/addRating", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDto> create(@RequestBody CreateRatingRequest newRating) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.create(newRating));
    }
    @RequestMapping(value = "/getAllRatingsByBookId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RatingDto>> getAllRatingsByBookId(@RequestParam(value = "id") Long id,
                                                                 @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                                 @RequestParam(value = "limit", defaultValue = "20") Integer limit) throws NotFoundException {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "rating"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.getAllRatingsByBookId(pageable, id));
    }

    @RequestMapping(value = "/updateRating", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDto> update(@RequestBody CreateRatingRequest updatedRating,
                                            @RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ratingService.update(updatedRating, id));
    }
    @RequestMapping(value = "/deleteRating/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") Long id) {
        ratingService.deleteRating(id);
    }

}

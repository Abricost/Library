package com.library.Library.controller;

import com.library.Library.dto.UserDto;
import com.library.Library.service.UserService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "basicAuth")
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                     @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.ASC, "username"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.listAll(pageable));
    }


}
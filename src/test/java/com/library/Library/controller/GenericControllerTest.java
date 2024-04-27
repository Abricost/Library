package com.library.Library.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.library.Library.service.userdetails.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public abstract class GenericControllerTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected WebApplicationContext webApplicationContext;

    @BeforeTestExecution
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .alwaysDo(print())
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    protected ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void prepare() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected abstract void createObject() throws Exception;

    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected abstract void updateObject() throws Exception;

//    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected abstract void deleteObject() throws Exception;

    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected abstract void listAll() throws Exception;

    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected abstract void listOne() throws Exception;

    protected String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}


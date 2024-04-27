package com.library.Library.controller;

import com.library.Library.dto.RatingDto;
import com.library.Library.dto.UserDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends GenericControllerTest {
    private static UserDto createdUserDto;


    @Override
    protected void createObject() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void updateObject() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void deleteObject() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Test
    @Order(1)
    @Override
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "admin")
    protected void listAll() throws Exception {
        String result = mvc.perform(
                        MockMvcRequestBuilders.get("/user/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Override
    protected void listOne() throws Exception {
    }
}

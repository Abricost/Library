package com.library.Library.controller;

import com.library.Library.dto.RatingDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RatingControllerTest extends GenericControllerTest {
    private static RatingDto createdRatingDto;

    @Test
    @Order(1)
    @Override
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "user1")
    protected void createObject() throws Exception {
        RatingDto ratingDto = new RatingDto(4, 314L, 10013L);
        String result = mvc.perform(
                        MockMvcRequestBuilders.post("/rating/addRating")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(ratingDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        createdRatingDto = objectMapper.readValue(result, RatingDto.class);
    }

    @Test
    @Order(2)
    @Override
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "admin")
    protected void updateObject() throws Exception {
        createdRatingDto.setRating(5);
        String result = mvc.perform(
                        MockMvcRequestBuilders.put("/rating/updateRating")
                                .param("id", String.valueOf(createdRatingDto.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(createdRatingDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(5)
    @Override
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "admin")
    protected void deleteObject() throws Exception {
        String result = mvc.perform(
                        MockMvcRequestBuilders.delete("/rating/deleteRating/{id}", createdRatingDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(3)
    @Override
    protected void listAll() throws Exception {
        String result = mvc.perform(
                        MockMvcRequestBuilders.get("/rating/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    @Order(4)
    @Override
    protected void listOne() throws Exception {
        String result = mvc.perform(
                        MockMvcRequestBuilders.get("/rating/getRating")
                                .param("id", String.valueOf(createdRatingDto.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(6)
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "admin")
    protected void getAllRatingsByBookId() throws Exception {
        String result = mvc.perform(
                        MockMvcRequestBuilders.get("/rating/getAllRatingsByBookId")
                                .param("id", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}

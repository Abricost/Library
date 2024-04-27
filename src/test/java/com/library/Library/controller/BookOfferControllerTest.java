package com.library.Library.controller;

import com.library.Library.dto.BookOfferDto;
import com.library.Library.dto.RatingDto;
import com.library.Library.dto.create.CreateBookOfferRequest;
import com.library.Library.dto.create.UpdateBookOfferDto;
import com.library.Library.model.Role;
import com.library.Library.model.Status;
import com.library.Library.model.User;
import com.library.Library.service.userdetails.CustomUserDetails;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.library.Library.model.Status.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookOfferControllerTest extends GenericControllerTest {
    private static BookOfferDto createdBookOfferDto;

    @Test
    @Order(1)
    @Override
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "user1")
    protected void createObject() throws Exception {
        CreateBookOfferRequest bookOfferRequest = new CreateBookOfferRequest("1", null, null, "createTest", null, null, null, null, null, null);
        String result = mvc.perform(
                        MockMvcRequestBuilders.post("/bookOffer/addBookOffer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(bookOfferRequest)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        createdBookOfferDto = objectMapper.readValue(result, BookOfferDto.class);
        Assertions.assertEquals(createdBookOfferDto.getStatus(), ADMIN_REVIEW);
    }

    @Test
    @Order(2)
    @Override
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "admin")
    protected void listAll() throws Exception {
        String result = mvc.perform(
                        MockMvcRequestBuilders.get("/bookOffer/list")
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
    @WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "admin")
    protected void updateObject() throws Exception {
        createdBookOfferDto.setComments("New comments");
        String result = mvc.perform(
                        MockMvcRequestBuilders.put("/bookOffer/updateBookOffer")
                                .param("id", String.valueOf(createdBookOfferDto.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(createdBookOfferDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(4)
    protected void updateObjectByUser() throws Exception {
        createdBookOfferDto.setOriginalTitle("Updated By User");
        String result = mvc.perform(
                        MockMvcRequestBuilders.put("/bookOffer/updateBookOffer")
                                .param("id", String.valueOf(createdBookOfferDto.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(createdBookOfferDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(4)
    protected void updateObjectByAdmin() throws Exception {
        UpdateBookOfferDto bookOfferDto = new UpdateBookOfferDto(null, null, null, "create Test", null, null, null, null, null, ADDED, null, "Comment");
        String result = mvc.perform(
                        MockMvcRequestBuilders.put("/bookOffer/updateBookOffer")
                                .param("id", String.valueOf(createdBookOfferDto.getId()))
                                //                       .headers()
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(bookOfferDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }


    @Test
    @Order(5)
    @Override
    protected void deleteObject() throws Exception {
        String result = mvc.perform(
                        MockMvcRequestBuilders.delete("/bookOffer/deleteBookOffer/", createdBookOfferDto.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }


    @Test
    @Order(2)
    @Override
    protected void listOne() throws Exception {
        String result = mvc.perform(
                        MockMvcRequestBuilders.get("/bookOffer/getBookOffer?id=1")
                                //                       .headers()
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
    protected void getBookOfferByUser() throws Exception {
        String result = mvc.perform(
                        MockMvcRequestBuilders.get("/bookOffers/getBookOfferByUserId?id=3")
                                //                       .headers()
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(7)
    protected void bookOfferTest() throws Exception {
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setId(1L);
        List<Role> adminRoles = new ArrayList<>();
        adminRoles.add(adminRole);

        Role userRole = new Role();
        userRole.setName("USER");
        userRole.setId(2L);

        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);

        User admin1 = new User();
        admin1.setId(439L);
        admin1.setUsername("admin1");
        admin1.setPassword("$2a$10$IRAa2L42Bz011.za0K6QrevQX2cGHIsbfouKkGdP9OU6S6klTsJn6");
        admin1.setRoles(adminRoles);

        User user1 = new User();
        admin1.setId(314L);
        admin1.setUsername("user1");
        admin1.setPassword("admin");
        admin1.setRoles(userRoles);

        User user2 = new User();
        admin1.setId(1185L);
        admin1.setUsername("user2");
        admin1.setPassword("$2a$10$IRAa2L42Bz011.za0K6QrevQX2cGHIsbfouKkGdP9OU6S6klTsJn6");
        admin1.setRoles(userRoles);

        CustomUserDetails userAdmin = new CustomUserDetails(admin1);
        CustomUserDetails user = new CustomUserDetails(user1);
        CustomUserDetails userUser2 = new CustomUserDetails(user2);
        String result1 = mvc.perform(
                        MockMvcRequestBuilders.get("/book/list")
                                .with(user(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String result2 = mvc.perform(
                        MockMvcRequestBuilders.get("/book/getBook?id=1")
                                .with(user(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        RatingDto ratingDto = new RatingDto(4, user.getId(), 1L);
        String result3 = mvc.perform(
                        MockMvcRequestBuilders.post("/rating/addRating")
                                .with(user(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(ratingDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String result4 = mvc.perform(
                        MockMvcRequestBuilders.get("/book/getBook?id=1")
                                .with(user(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CreateBookOfferRequest BookOfferDto = new CreateBookOfferRequest("1", null, null, "createTest", null, null, null, null, null, null);
        String result5 = mvc.perform(
                        MockMvcRequestBuilders.post("/bookOffer/addBookOffer")
                                .with(user(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(BookOfferDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String result6 = mvc.perform(
                        MockMvcRequestBuilders.get("/bookOffer/list")
                                .with(user(user))
//                        .headers()
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String result7 = mvc.perform(
                        MockMvcRequestBuilders.get("/bookOffer/list")
                                .with(user(userUser2))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UpdateBookOfferDto BookOfferUpdateByAdmin = new UpdateBookOfferDto("1", null, null, "create Test", null, null, null, null, null, REJECTED, null, "Comment");
        String result8 = mvc.perform(
                        MockMvcRequestBuilders.put("/bookOffer/updateBookOffer?id=1")
                                .with(user(userAdmin))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(BookOfferUpdateByAdmin)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UpdateBookOfferDto BookOfferUpdateByUser = new UpdateBookOfferDto("1", null, "Add", "create Test", null, null, null, null, null, ADMIN_REVIEW, null, "Comment");

        String result9 = mvc.perform(
                        MockMvcRequestBuilders.post("/bookOffer/updateBookOffer?id=1")
                                .with(user(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(BookOfferUpdateByUser)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UpdateBookOfferDto BookOfferUpdateByUserOk = new UpdateBookOfferDto("1", null, "Add", "create Test", null, null, null, null, null, REJECTED, null, "Comment");

        String result10 = mvc.perform(
                        MockMvcRequestBuilders.put("/bookOffer/updateBookOffer?id=1")
                                .with(user(userAdmin))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(BookOfferUpdateByAdmin)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String result11 = mvc.perform(
                        MockMvcRequestBuilders.get("/book/list")
                                .with(user(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        String result12 = mvc.perform(
                        MockMvcRequestBuilders.get("/book/list")
                                .with(user(userUser2))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }
}

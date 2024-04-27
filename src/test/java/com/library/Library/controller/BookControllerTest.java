package com.library.Library.controller;

import com.library.Library.dto.BookDto;
import com.library.Library.dto.create.CreateBookRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends GenericControllerTest {
    private static BookDto createdBookDto;

    @Test
    @Order(1)
    @Override
    protected void createObject() throws Exception {
        CreateBookRequest bookDto = new CreateBookRequest(null, null, "createTest", null, null, null, null, null, null);
        String result = mvc.perform(
                        MockMvcRequestBuilders.post("/book/addBook")
                                //                       .headers()
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(bookDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        createdBookDto = objectMapper.readValue(result, BookDto.class);
    }

    @Test
    @Order(2)
    @Override
    protected void updateObject() throws Exception {
        createdBookDto.setTitle("Updated Book");
        String result = mvc.perform(
                        MockMvcRequestBuilders.put("/book/updateBook")
                                .param("id", String.valueOf(createdBookDto.getId()))
                                //                       .headers()
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(asJsonString(createdBookDto)))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BookDto updatedBook = objectMapper.readValue(result, BookDto.class);
        Assertions.assertEquals(createdBookDto.getId(), updatedBook.getId());
        Assertions.assertEquals(createdBookDto.getTitle(), updatedBook.getTitle());
    }

    @Test
    @Order(5)
    @Override
    protected void deleteObject() throws Exception {
        String result = mvc.perform(
                        MockMvcRequestBuilders.delete("/book/deleteBook/{id}", createdBookDto.getId())
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
                        MockMvcRequestBuilders.get("/book/list")
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
                        MockMvcRequestBuilders.get("/book/getBook")
                                .param("id", String.valueOf(createdBookDto.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

}

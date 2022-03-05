package com.kaueoliveira.sysc4806;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Sysc4806ApplicationTest {

    @Autowired
    private MockMvc mvc;

    private String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch(JsonProcessingException e) {
            return "";
        }
    }

    @Test
    public void testCreateBook() throws Exception {
        mvc.perform(post("/createbook").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(toJson(new AddressBook(1)))));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mvc.perform(post("/createbook"));
        mvc.perform(post("/deletebook?bookId=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBuddies() throws Exception {
        mvc.perform(post("/createbook"));
        mvc.perform(post("/addbuddy?bookId=1").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new BuddyInfo("test", "test"))));
        mvc.perform(get("/getbuddies?bookId=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(equalTo(toJson(new ArrayList<BuddyInfo>() {{
                    add(new BuddyInfo("test", "test", 1));
                }}))));
    }

    @Test
    public void testAddBuddy() throws Exception {
        mvc.perform(post("/createbook"));
        mvc.perform(post("/addbuddy?bookId=1").contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new BuddyInfo("test", "test"))))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(toJson(new AddressBook(1) {{
                    addBuddy(new BuddyInfo("test", "test", 1));
                }}))));
    }

    @Test
    public void testRemoveBuddy() throws Exception {
        mvc.perform(post("/createbook"));
        mvc.perform(post("/addbuddy?bookId=1").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new BuddyInfo("test", "test"))));
        mvc.perform(post("/addbuddy?bookId=1").contentType(MediaType.APPLICATION_JSON)
                .content(toJson(new BuddyInfo("test1", "test1"))));
        mvc.perform(post("/deletebuddy?bookId=1").contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(new BuddyInfo("test", "test")))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(toJson(new AddressBook(1) {{
                    addBuddy(new BuddyInfo("test1", "test1", 2));
                }}))));
    }
}

package ua.in.smartjava.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import ua.in.smartjava.domain.board.Board;
import ua.in.smartjava.domain.board.BoardRepository;

import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BoardsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BoardRepository boardRepository;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void cleanup() {
        boardRepository.deleteAll();
    }

    @Test
    public void getAllBoards() throws Exception {
        mockMvc.perform(get("/boards"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllBoardsWithInsert() throws Exception {

        boardRepository.save(new Board("HEX", "FLIGHT"));

        mockMvc.perform(get("/boards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", iterableWithSize(1)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].flight").value("FLIGHT"))
                .andExpect(jsonPath("$[0].hex").value("HEX"));
    }

}
package com.stackroute.gipherrecommendersystem.controller;

import com.stackroute.gipherrecommendersystem.model.BookmarkDTO;
import com.stackroute.gipherrecommendersystem.service.RecommendationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RecommendationController.class)
public class RecommendationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    RecommendationService recommendationService;

    BookmarkDTO bookmarkDTO;
    List<BookmarkDTO> gifsList;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        bookmarkDTO=new BookmarkDTO("123","abc","http://url",null,1);
        gifsList=new ArrayList<>();
        gifsList.add(bookmarkDTO);
    }

    @After
    public void tearDown(){
        bookmarkDTO=null;
        gifsList=null;
    }

    @Test
    public void getAllRecommendationsTest() throws Exception{
        when(recommendationService.getAllRecommendations()).thenReturn(gifsList);
        mockMvc.perform(get("/api/v1/recommendations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        verify(recommendationService,times(1)).getAllRecommendations();
    }
}
package com.stackroute.giphermanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.giphermanager.model.BookmarkDTO;
import com.stackroute.giphermanager.model.UserDTO;
import com.stackroute.giphermanager.proxy.GifRecommendationProxy;
import com.stackroute.giphermanager.service.GifManagerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(GifManagerController.class)
public class GifManagerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    GifManagerService gifManagerService;
    @MockBean
    GifRecommendationProxy gifRecommendationProxy;
    @InjectMocks
    GifManagerController gifManagerController;

    UserDTO userDTO;
    BookmarkDTO bookmarkDTO;
    List<BookmarkDTO> list;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(gifManagerController).build();
        bookmarkDTO=new BookmarkDTO("abc","title","http://url","abc", 1);
        list=new ArrayList<>();
        list.add(bookmarkDTO);
        userDTO=new UserDTO("user","email@gmail.com",list);
    }

    @After
    public void tearDown(){
        userDTO=null;
        bookmarkDTO=null;
        list=null;
    }

    @Test
    public void saveBookmarkDTOTest() throws Exception {
        when(gifRecommendationProxy.saveGifToRecommendations(bookmarkDTO)).thenReturn(bookmarkDTO);
        when(gifManagerService.saveBookmarkGif("user", list.get(0))).thenReturn(userDTO);
        ResponseEntity<?> responseEntity = gifManagerController.saveBookmarkGif(list.get(0), "user");
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Assert.assertEquals(responseEntity.getBody(),list.get(0));
    }

    @Test
    public void deleteBookmarkDTOTest() throws Exception{
        when(gifManagerService.deleteBookmarkGif(userDTO.getUsername(),bookmarkDTO.getId())).thenReturn(userDTO);
        mockMvc.perform(delete("/api/v1/gifmanager/user/{username}/{id}",userDTO.getUsername(),bookmarkDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(bookmarkDTO)))
                .andExpect(status().isOk())
                .andDo(print());
        verify(gifManagerService,times(1)).deleteBookmarkGif(userDTO.getUsername(),bookmarkDTO.getId());
    }


    @Test
    public void getAllBookmarkDTOTest() throws Exception{
        when(gifManagerService.getAllBookmarkGifs(userDTO.getUsername())).thenReturn(userDTO.getBookmarkDTOList());
        ResponseEntity<?> responseEntity = gifManagerController.getAllBookmarkGifs(userDTO.getUsername());
        Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(responseEntity.getBody(),userDTO.getBookmarkDTOList());
    }

    private static String toJson(BookmarkDTO bookmarkDTO){
        String result=null;
        try {
            final ObjectMapper objectMapper=new ObjectMapper();
            final String json=objectMapper.writeValueAsString(bookmarkDTO);
            result=json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;

    }
}
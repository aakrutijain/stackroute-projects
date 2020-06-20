package com.stackroute.gipherrecommendersystem.service;

import com.stackroute.gipherrecommendersystem.model.BookmarkDTO;
import com.stackroute.gipherrecommendersystem.repository.RecommendationRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class RecommendationServiceTest {

    @Mock
    RecommendationRepository recommendationRepository;

    @InjectMocks
    RecommendationServiceImpl recommendationService;

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
    public void existingRecommendationsTest() throws Exception{
        when(recommendationRepository.findById(bookmarkDTO.getId())).thenReturn(Optional.of(bookmarkDTO));
        BookmarkDTO fetchedGif=recommendationService.saveGifToRecommendations(bookmarkDTO);
        Assert.assertEquals(bookmarkDTO.getId(),fetchedGif.getId());
        Assert.assertEquals(2,fetchedGif.getDownloadCount());
        verify(recommendationRepository,times(1)).findById(bookmarkDTO.getId());
        verify(recommendationRepository,times(1)).save(bookmarkDTO);
    }

    @Test
    public void newRecommendationsTest() throws Exception{
        when(recommendationRepository.findById(bookmarkDTO.getId())).thenReturn(Optional.ofNullable(null));
        BookmarkDTO fetchedGif=recommendationService.saveGifToRecommendations(bookmarkDTO);
        Assert.assertEquals(bookmarkDTO.getId(),fetchedGif.getId());
        Assert.assertEquals(1,fetchedGif.getDownloadCount());
        verify(recommendationRepository,times(1)).findById(bookmarkDTO.getId());
        verify(recommendationRepository,times(1)).insert(bookmarkDTO);
    }

    @Test
    public void getAllRecommendations() throws Exception{
        bookmarkDTO=new BookmarkDTO("234","abc","http://url",null,1);
        gifsList.add(bookmarkDTO);
        when(recommendationRepository.findAll()).thenReturn(gifsList);
        List<BookmarkDTO> fetchedList=recommendationService.getAllRecommendations();
        Assert.assertEquals(2,fetchedList.size());
        verify(recommendationRepository,times(1)).findAll();
    }

}
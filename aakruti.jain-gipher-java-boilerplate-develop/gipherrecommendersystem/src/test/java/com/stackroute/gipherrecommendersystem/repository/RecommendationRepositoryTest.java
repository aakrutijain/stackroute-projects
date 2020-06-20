package com.stackroute.gipherrecommendersystem.repository;

import com.stackroute.gipherrecommendersystem.model.BookmarkDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecommendationRepositoryTest {

    @Autowired
    RecommendationRepository recommendationRepository;

    BookmarkDTO gif;

    @Before
    public void setup(){
        gif=new BookmarkDTO();
        gif.setId("123");
        gif.setTitle("abcdefgh");
        gif.setUrl("http://abc");
    }

    @After
    public void tearDown(){
        gif=null;
        recommendationRepository.deleteAll();
    }

    @Test
    public void saveRecommendation(){
        recommendationRepository.save(gif);
        Optional<BookmarkDTO> gifs=recommendationRepository.findById(gif.getId());
        BookmarkDTO fetchedGIF=gifs.get();

        Assert.assertEquals(gif.getId(),fetchedGIF.getId());
    }

}
package com.stackroute.giphermanager.repository;

import com.stackroute.giphermanager.model.BookmarkDTO;
import com.stackroute.giphermanager.model.UserDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest
public class GifManagerRepositoryTest {

    @Autowired
    GifManagerRepository gifManagerRepository;

    private BookmarkDTO bookmarkDTO;
    private UserDTO user;

    @Before
    public void setup(){
        bookmarkDTO =new BookmarkDTO("123","gif-title","http://url",null, 1);
        ArrayList<BookmarkDTO> gifs=new ArrayList<>();
        gifs.add(bookmarkDTO);
        user=new UserDTO("abc","email@gamil.com",gifs);
    }

    @After
    public void teardown(){
        user=null;
        bookmarkDTO =null;
        gifManagerRepository.deleteAll();
    }

    @Test
    public void saveUserGIFsTest(){
        gifManagerRepository.insert(user);
        List<UserDTO> fetchedUserDTO = gifManagerRepository.findByUsername(user.getUsername());
        Assert.assertEquals(fetchedUserDTO.get(0).getBookmarkDTOList().get(0).getId(), bookmarkDTO.getId());
    }

    @Test
    public void getAllBookmarksTest(){
        bookmarkDTO =new BookmarkDTO("345","gif-title","http://url",null, 1);
        user.getBookmarkDTOList().add(bookmarkDTO);
        gifManagerRepository.save(user);
        List<BookmarkDTO> list= gifManagerRepository.findByUsername(user.getUsername()).get(0).getBookmarkDTOList();
        Assert.assertEquals(2,list.size());
        Assert.assertEquals("123",list.get(0).getId());
        Assert.assertEquals("345",list.get(1).getId());
    }


}
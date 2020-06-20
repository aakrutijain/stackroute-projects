package com.stackroute.giphermanager.service;

import com.stackroute.giphermanager.config.RabbitMQMessageProducer;
import com.stackroute.giphermanager.exception.GifAlreadyExistsException;
import com.stackroute.giphermanager.exception.GifNotFoundException;
import com.stackroute.giphermanager.exception.UserNotFoundException;
import com.stackroute.giphermanager.model.BookmarkDTO;
import com.stackroute.giphermanager.model.UserDTO;
import com.stackroute.giphermanager.repository.GifManagerRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
public class GifManagerServiceTest {

    @Mock
    GifManagerRepository gifManagerRepository;
    @Mock
    RabbitMQMessageProducer rabbitMQMessageProducer;

    private BookmarkDTO bookmarkDTO;
    private UserDTO user;

    private Optional optional;
    private List<BookmarkDTO> list=null;
    private List<UserDTO> userDTOS;

    @InjectMocks
    private GifManagerServiceImpl gifServiceImpl;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        bookmarkDTO =new BookmarkDTO("123","title","http://url",null, 1);
        list=new ArrayList<>();
        list.add(bookmarkDTO);
        user=new UserDTO("abc","email@gmail.com",list);
        optional=Optional.of(bookmarkDTO);

        userDTOS = new ArrayList<>();
        userDTOS.add(user);
    }

    @After
    public void tearDown(){
        bookmarkDTO =null;
    }

    @Test(expected =  GifAlreadyExistsException.class)
    public void saveBookmarkDTOSuccessTest() throws GifAlreadyExistsException, UserNotFoundException {
        user=new UserDTO("xyz","email@gmail.com",null);
        when(gifManagerRepository.findByUsername(user.getUsername())).thenReturn(userDTOS);

        UserDTO fetchedUserDTO =gifServiceImpl.saveBookmarkGif(user.getUsername(),bookmarkDTO);

        assertThat(fetchedUserDTO).isEqualToComparingFieldByField(user);
        verify(gifManagerRepository,times(1)).findByUsername(user.getUsername());
        verify(gifManagerRepository,times(1)).save(user);
    }

    @Test(expected = GifAlreadyExistsException.class)
    public void saveBookmarkDTOFailureTest() throws GifAlreadyExistsException, UserNotFoundException {
        when(gifManagerRepository.findByUsername(user.getUsername())).thenReturn(userDTOS);

        UserDTO gipherUser =gifServiceImpl.saveBookmarkGif(user.getUsername(),bookmarkDTO);

        Assert.assertEquals(gipherUser, user);
        verify(gifManagerRepository,times(1)).findByUsername(user.getUsername());
        verify(gifManagerRepository,times(1)).save(user);
    }

    @Test
    public void deleteBookmarkDTO() throws GifNotFoundException, UserNotFoundException {
        when(gifManagerRepository.findByUsername(user.getUsername())).thenReturn(userDTOS);
        UserDTO gipherUser=gifServiceImpl.deleteBookmarkGif(user.getUsername(),bookmarkDTO.getId());
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("abc");
        userDTO.setBookmarkDTOList(new ArrayList<>());
        assertThat(gipherUser).isEqualToComparingFieldByField(userDTO);
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteBookmarkDTOUserNotFoundException() throws GifNotFoundException, UserNotFoundException {
        when(gifManagerRepository.findByUsername(user.getUsername())).thenReturn(null);
        UserDTO gipherUser=gifServiceImpl.deleteBookmarkGif(user.getUsername(),bookmarkDTO.getId());
    }

    @Test(expected = GifNotFoundException.class)
    public void deleteBookmarkDTOGifNotFoundException() throws GifNotFoundException, UserNotFoundException {
        user=new UserDTO("xyz","email@gmail.com",new ArrayList<>());
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(user);
        when(gifManagerRepository.findByUsername(user.getUsername())).thenReturn(userDTOS);
        UserDTO gipherUser=gifServiceImpl.deleteBookmarkGif(user.getUsername(),bookmarkDTO.getId());
    }


    @Test
    public void getAllBookmarkDTO() throws UserNotFoundException,Exception{
        when(gifManagerRepository.findByUsername(user.getUsername())).thenReturn(userDTOS);
        List<BookmarkDTO> fetchedList=gifServiceImpl.getAllBookmarkGifs(user.getUsername());
        Assert.assertEquals(list,fetchedList);

        verify(gifManagerRepository,times(1)).findByUsername(user.getUsername());
    }
}
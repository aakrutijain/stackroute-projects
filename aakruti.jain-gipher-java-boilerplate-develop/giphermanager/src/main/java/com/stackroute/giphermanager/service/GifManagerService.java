package com.stackroute.giphermanager.service;

import com.stackroute.giphermanager.exception.GifAlreadyExistsException;
import com.stackroute.giphermanager.exception.GifNotFoundException;
import com.stackroute.giphermanager.exception.UserNotFoundException;
import com.stackroute.giphermanager.model.BookmarkDTO;
import com.stackroute.giphermanager.model.UserDTO;

import java.util.List;

public interface GifManagerService {

    UserDTO saveBookmarkGif(String username, BookmarkDTO favouriteGIF) throws UserNotFoundException, GifAlreadyExistsException;
    UserDTO deleteBookmarkGif(String user,String id) throws UserNotFoundException, GifNotFoundException;
    List<BookmarkDTO> getAllBookmarkGifs(String username) throws UserNotFoundException,Exception;
    UserDTO createNewUser(UserDTO user);

}
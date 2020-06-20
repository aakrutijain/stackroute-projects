package com.stackroute.giphermanager.controller;

import com.stackroute.giphermanager.exception.GifAlreadyExistsException;
import com.stackroute.giphermanager.exception.GifNotFoundException;
import com.stackroute.giphermanager.exception.UserNotFoundException;
import com.stackroute.giphermanager.model.BookmarkDTO;
import com.stackroute.giphermanager.model.UserDTO;
import com.stackroute.giphermanager.proxy.GifRecommendationProxy;
import com.stackroute.giphermanager.service.GifManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/gifmanager")
public class GifManagerController {

    @Autowired
    GifRecommendationProxy gifRecommendationProxy;

    GifManagerService gifManagerService;

    public GifManagerController(GifManagerService gifManagerService){
        this.gifManagerService=gifManagerService;
    }

    @PostMapping("user/{username}/bookmark")
    ResponseEntity<?> saveBookmarkGif(@RequestBody BookmarkDTO bookmarkDTO, @PathVariable("username") String username) throws UserNotFoundException, GifAlreadyExistsException {
        gifRecommendationProxy.saveGifToRecommendations(bookmarkDTO);
        gifManagerService.saveBookmarkGif(username,bookmarkDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmarkDTO);
    }

    @DeleteMapping("user/{username}/{id}")
    ResponseEntity<?> deleteBookmarkGif(@PathVariable("id") String id,@PathVariable("username") String username) throws UserNotFoundException, GifNotFoundException {
        UserDTO gipherUser=gifManagerService.deleteBookmarkGif(username,id);
        return ResponseEntity.status(HttpStatus.OK).body(gipherUser);
    }

    @GetMapping("user/{username}/bookmarks")
    ResponseEntity<?> getAllBookmarkGifs(@PathVariable("username") String username) throws UserNotFoundException,Exception{
        return ResponseEntity.status(HttpStatus.OK).body(gifManagerService.getAllBookmarkGifs(username));
    }


}
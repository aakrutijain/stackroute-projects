package com.stackroute.giphermanager.service;

import com.stackroute.giphermanager.config.RabbitMQMessageProducer;
import com.stackroute.giphermanager.exception.GifAlreadyExistsException;
import com.stackroute.giphermanager.exception.GifNotFoundException;
import com.stackroute.giphermanager.exception.UserNotFoundException;
import com.stackroute.giphermanager.model.BookmarkDTO;
import com.stackroute.giphermanager.model.UserDTO;
import com.stackroute.giphermanager.proxy.GifRecommendationProxy;
import com.stackroute.giphermanager.repository.GifManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GifManagerServiceImpl implements GifManagerService {

    GifManagerRepository gifRepository;
    RabbitMQMessageProducer rabbitMQMessageProducer;

    @Autowired
    public GifManagerServiceImpl(GifManagerRepository gifRepository, RabbitMQMessageProducer rabbitMQMessageProducer){
        this.gifRepository=gifRepository;
        this.rabbitMQMessageProducer=rabbitMQMessageProducer;
    }


    @Override
    public UserDTO saveBookmarkGif(String username, BookmarkDTO bookmarkGIF) throws UserNotFoundException, GifAlreadyExistsException {
        List<UserDTO> user=gifRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        if(user.isEmpty()){
            userDTO.setBookmarkDTOList(new ArrayList<>());
            List<BookmarkDTO> fetchedList=new ArrayList<>();
            fetchedList.add(bookmarkGIF);
            userDTO.setBookmarkDTOList(fetchedList);
            createNewUser(userDTO);
        } else {
            List<BookmarkDTO> fetchedList = createBookmarkList(user);
            for(BookmarkDTO gifObj:fetchedList) {
                if(gifObj.getId().equals(bookmarkGIF.getId())){
                    throw new GifAlreadyExistsException();
                }
            }
            gifRepository.deleteByUsername(username);
            fetchedList.add(bookmarkGIF);
            userDTO.setBookmarkDTOList(fetchedList);
            gifRepository.save(userDTO);
        }

        BookmarkDTO bookmarkDTO=new BookmarkDTO(bookmarkGIF.getId(),bookmarkGIF.getTitle(),bookmarkGIF.getUrl(),bookmarkGIF.getComments(),1);
//        rabbitMQMessageProducer.sendMessageToReccomendationQueue(bookmarkDTO);
        return userDTO;
    }

    @Override
    public UserDTO deleteBookmarkGif(String username,String id) throws UserNotFoundException, GifNotFoundException {
        List<UserDTO> user=gifRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        if(user==null){
            throw new UserNotFoundException();
        }
        List<BookmarkDTO> fetchedList=createBookmarkList(user);
        if(fetchedList.size()>0){
            gifRepository.deleteByUsername(username);
            for(int i=0;i<fetchedList.size();i++){
                if(id.equals(fetchedList.get(i).getId())){
                    fetchedList.remove(i);
                    userDTO.setBookmarkDTOList(fetchedList);
                    gifRepository.save(userDTO);
                    break;
                }
            }
        }else{
            throw new GifNotFoundException();
        }
        return userDTO;
    }

    @Override
    public List<BookmarkDTO> getAllBookmarkGifs(String username) throws Exception {
        List<UserDTO> user=gifRepository.findByUsername(username);
        if(user==null){
            throw new UserNotFoundException();
        }

        return createBookmarkList(user);
    }

    @Override
    public UserDTO createNewUser(UserDTO user) {
        gifRepository.save(user);
        return user;
    }

    private List<BookmarkDTO> createBookmarkList(List<UserDTO> user) {
        List<String> ids = new ArrayList<>();
        List<BookmarkDTO> bookmarkDTOS = new ArrayList<>();
        for(UserDTO userDTO:user) {
            for(BookmarkDTO bookmarkDTO:userDTO.getBookmarkDTOList()) {
                if(!ids.contains(bookmarkDTO.getId())) {
                    bookmarkDTOS.add(bookmarkDTO);
                    ids.add(bookmarkDTO.getId());
                }
            }
        }
        return bookmarkDTOS;
    }
}
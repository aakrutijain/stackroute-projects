package com.stackroute.giphermanager.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection="bookmarks")
@TypeAlias("bookmark")
public class UserDTO {

    @Id
    private String username;
    private String email;
    private List<BookmarkDTO> bookmarkDTOList;

    public UserDTO() {
    }

    public UserDTO(String username, String email, List<BookmarkDTO> bookmarkDTOList) {
        this.username = username;
        this.email = email;
        this.bookmarkDTOList = bookmarkDTOList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BookmarkDTO> getBookmarkDTOList() {
        return bookmarkDTOList;
    }

    public void setBookmarkDTOList(List<BookmarkDTO> bookmarkDTOList) {
        this.bookmarkDTOList = bookmarkDTOList;
    }
}

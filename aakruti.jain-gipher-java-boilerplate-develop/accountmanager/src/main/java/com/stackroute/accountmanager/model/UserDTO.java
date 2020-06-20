package com.stackroute.accountmanager.model;

import java.util.List;

public class UserDTO {

    private String username;
    private String email;
    private List<BookmarkDTO> bookmarkDTOList;

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

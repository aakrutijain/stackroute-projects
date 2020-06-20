package com.stackroute.giphermanager.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BookmarkDTO {

    private String id;
    private String title;
    private String url;
    private String comments;
    private int downloadCount;

    public BookmarkDTO(String id, String title, String url, String comments, int downloadCount) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.comments = comments;
        this.downloadCount = downloadCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }
}

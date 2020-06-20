package com.stackroute.gipherrecommendersystem.service;

import com.stackroute.gipherrecommendersystem.model.BookmarkDTO;

import java.util.List;

public interface RecommendationService {
    List<BookmarkDTO> getAllRecommendations() throws Exception;
    BookmarkDTO saveGifToRecommendations(BookmarkDTO bookmarkDTO) throws Exception;
}
package com.stackroute.gipherrecommendersystem.controller;

import com.stackroute.gipherrecommendersystem.model.BookmarkDTO;
import com.stackroute.gipherrecommendersystem.service.RecommendationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/recommendations")
public class RecommendationController<requestMapping> {

    RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService){
        this.recommendationService=recommendationService;
    }

    @GetMapping
    public List<BookmarkDTO> getAllRecommendations() throws Exception{
        return recommendationService.getAllRecommendations();
    }

    @PostMapping("/save")
    public BookmarkDTO saveGifToRecommendations(@RequestBody BookmarkDTO bookmarkDTO) throws Exception{
        return recommendationService.saveGifToRecommendations(bookmarkDTO);
    }
}
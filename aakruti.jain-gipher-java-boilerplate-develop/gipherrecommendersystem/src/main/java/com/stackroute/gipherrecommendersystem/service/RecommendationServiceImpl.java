package com.stackroute.gipherrecommendersystem.service;

import com.stackroute.gipherrecommendersystem.model.BookmarkDTO;
import com.stackroute.gipherrecommendersystem.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationServiceImpl implements RecommendationService{

    RecommendationRepository recommendationsRepository;

    @Autowired
    public RecommendationServiceImpl(RecommendationRepository recommendationsRepository){
        this.recommendationsRepository=recommendationsRepository;
    }

    @Override
    public List<BookmarkDTO> getAllRecommendations() throws Exception {
        return recommendationsRepository.findAll();
    }

    @Override
    public BookmarkDTO saveGifToRecommendations(BookmarkDTO bookmarkDTO) throws Exception {
        Optional<BookmarkDTO> fetchedOptional=recommendationsRepository.findById(bookmarkDTO.getId());
        if(fetchedOptional.isPresent()){
            BookmarkDTO fetchedGIF=fetchedOptional.get();
            int presentCount=fetchedGIF.getDownloadCount();
            fetchedGIF.setDownloadCount(++presentCount);
            bookmarkDTO=fetchedGIF;
            recommendationsRepository.save(fetchedGIF);
        }else{
            bookmarkDTO.setDownloadCount(1);
            recommendationsRepository.insert(bookmarkDTO);
        }
        return bookmarkDTO;
    }
}
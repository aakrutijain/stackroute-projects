package com.stackroute.gipherrecommendersystem.config;

import com.stackroute.gipherrecommendersystem.model.BookmarkDTO;
import com.stackroute.gipherrecommendersystem.service.RecommendationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessageConsumer {

    RecommendationService recommendationService;

    @Autowired
    RabbitMQMessageConsumer(RecommendationService recommendationService){
        this.recommendationService=recommendationService;
    }

    @RabbitListener(queues="recommendations_queue")
    public void getFavouriteGIFDTO(BookmarkDTO favouriteGIFDTO) throws Exception{
        BookmarkDTO bookmarkDTO=new BookmarkDTO();
        bookmarkDTO.setId(favouriteGIFDTO.getId());
        bookmarkDTO.setTitle(favouriteGIFDTO.getTitle());
        bookmarkDTO.setUrl(favouriteGIFDTO.getUrl());

        recommendationService.saveGifToRecommendations(bookmarkDTO);
    }

}
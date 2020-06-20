package com.stackroute.giphermanager.proxy;

import com.stackroute.giphermanager.model.BookmarkDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gipherrecommendations")
public interface GifRecommendationProxy {

    @PostMapping("/api/v1/recommendations/save")
    public BookmarkDTO saveGifToRecommendations(@RequestBody BookmarkDTO bookmarkDTO);
}

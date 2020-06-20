package com.stackroute.gipherrecommendersystem.repository;

import com.stackroute.gipherrecommendersystem.model.BookmarkDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends MongoRepository<BookmarkDTO,String> {
}
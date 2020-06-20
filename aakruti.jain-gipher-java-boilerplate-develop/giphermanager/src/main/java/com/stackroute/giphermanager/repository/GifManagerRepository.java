
package com.stackroute.giphermanager.repository;

import com.stackroute.giphermanager.model.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GifManagerRepository extends MongoRepository<UserDTO,String> {
    List<UserDTO> findByUsername(String username);
    void deleteByUsername(String username);
}
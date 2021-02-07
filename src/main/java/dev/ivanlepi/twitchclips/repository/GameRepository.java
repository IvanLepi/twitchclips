package dev.ivanlepi.twitchclips.repository;

import dev.ivanlepi.twitchclips.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "games")
public interface GameRepository extends MongoRepository<Game, String> {

    
}

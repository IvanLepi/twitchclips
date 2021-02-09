package dev.ivanlepi.twitchclips.repository;

import dev.ivanlepi.twitchclips.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
}

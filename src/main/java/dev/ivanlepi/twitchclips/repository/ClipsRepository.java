package dev.ivanlepi.twitchclips.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import dev.ivanlepi.twitchclips.models.Clip;

public interface ClipsRepository extends MongoRepository<Clip, String>, ClipsCustomRepository {
    
}

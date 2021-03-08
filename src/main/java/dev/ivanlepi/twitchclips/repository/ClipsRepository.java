package dev.ivanlepi.twitchclips.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import dev.ivanlepi.twitchclips.models.Clip;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ClipsRepository extends MongoRepository<Clip, String>, ClipsCustomRepository {
    
    //Trending Clips Query
    @Query("{ 'created_at' : { $gte: ?0, $lt: ?1}, 'view_count' : { $gt: 200, $lt: 10000000 } }")
    List<Clip> findTrendingClips(String dateGT, String dateLT);
    
}

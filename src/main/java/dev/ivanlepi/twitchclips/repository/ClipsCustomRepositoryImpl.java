package dev.ivanlepi.twitchclips.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import dev.ivanlepi.twitchclips.models.Clip;

@Repository
public class ClipsCustomRepositoryImpl implements ClipsCustomRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    /**
	  * This method returns the list of Clips for selected game and or broadcaster from our database.
	  * @param game_id Optional parameter to find all documents withat specified game_id
      * @param broadcaster_id Optional parameter to find all documents with specified broadcaster_id
	  * @return List<Clip> This returns list of Clips from our database with specified Criteria.
	  */
    public List<Clip> findBy(String game_id, String broadcaster_id, Pageable page){
        final Query query = new Query().with(page);

        final List<Criteria> criteria = new ArrayList<Criteria>();

        if(broadcaster_id != null && !broadcaster_id.isEmpty()){
            criteria.add(Criteria.where("broadcaster_id").is(broadcaster_id));
        }
        if(game_id != null && !game_id.isEmpty()){
            criteria.add(Criteria.where("game_id").is(game_id));
        }

        if(!criteria.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }
        return mongoTemplate.find(query, Clip.class);
    }
    
}

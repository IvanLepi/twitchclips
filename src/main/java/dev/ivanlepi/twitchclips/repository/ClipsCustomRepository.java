package dev.ivanlepi.twitchclips.repository;
import java.util.List;

import dev.ivanlepi.twitchclips.models.Clip;

public interface ClipsCustomRepository {
     
    public List<Clip> findBy(String game_id, String broadcaster_id);
}

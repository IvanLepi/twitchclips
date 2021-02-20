package dev.ivanlepi.twitchclips.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import dev.ivanlepi.twitchclips.models.Clip;

public interface ClipsCustomRepository {
     
    public Page<Clip> findBy(String game_id, String broadcaster_id, Pageable page);
}

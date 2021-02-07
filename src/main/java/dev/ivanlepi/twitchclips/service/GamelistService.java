package dev.ivanlepi.twitchclips.service;

import dev.ivanlepi.twitchclips.models.Clip;
import dev.ivanlepi.twitchclips.models.Game;
import dev.ivanlepi.twitchclips.repository.GameRepository;
import java.util.List;

public class GamelistService {

    private final GameRepository repository;

	public GamelistService(GameRepository repository){
		this.repository = repository;
	}

	// Returns the list of games from our database
	public List<Game> getGames(){
		return repository.findAll();
	}
	
    // Return the list of Clips for selected game_id
    // TODO write this method
    public List<Clip> getClips(String game_id) {
        return null;
    }

}

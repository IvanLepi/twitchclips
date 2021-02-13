package dev.ivanlepi.twitchclips.service;

import dev.ivanlepi.twitchclips.models.Clip;
import dev.ivanlepi.twitchclips.models.Game;
import dev.ivanlepi.twitchclips.repository.GameRepository;
import dev.ivanlepi.twitchclips.repository.ClipsRepository;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class GamelistService {

    private final GameRepository gameRepository;
	private final ClipsRepository clipRepository;

	public GamelistService(GameRepository gameRepository, ClipsRepository clipRepository) {
		this.gameRepository = gameRepository;
		this.clipRepository = clipRepository;
	}

	/**
	 * This method returns the list of games from our database.
	 */
	public List<Game> getGames(){
		return gameRepository.findAll();
	}

	/**
	 * This method returns the list of Clips for selected game.
	 * @param game_id Every Clip object has game_id parameter.
	 * @return Page<Clip> This returns list of Clips.
	 */
	public Page<Clip> getClips(String game_id,Pageable page){
		final Example<Clip> example = Example.of(new Clip(game_id));
		return clipRepository.findAll(example, page);
	}
	
}

package dev.ivanlepi.twitchclips.service;

import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import dev.ivanlepi.twitchclips.models.Game;
import dev.ivanlepi.twitchclips.models.Feed;
import dev.ivanlepi.twitchclips.models.ClipsFeed;
import dev.ivanlepi.twitchclips.models.Clip;

import dev.ivanlepi.twitchclips.repository.GameRepository;
import dev.ivanlepi.twitchclips.repository.ClipsRepository;

@Service
public class Twitch extends ApiBinding {

    private static final String TWITCH_API_BASE_URL = "https://api.twitch.tv/helix";

    private final GameRepository gameRepository;

    private final ClipsRepository clipRepository;

    public Twitch(String accessToken, GameRepository gameRepository, ClipsRepository clipRepository) {
        super(accessToken);
        this.gameRepository = gameRepository;
        this.clipRepository = clipRepository;
    }
 
    /**
     * This method updates our database with top 20 games from Twitch API.
     * @return Nothing.
     */
    public List<Game> updateGames() {
        List<Game> listOfGames = restTemplate.getForObject(TWITCH_API_BASE_URL +
        "/games/top?first=100", Feed.class).getData();
                
        // Empty the database to see if its updating properly
        gameRepository.deleteAll();

        // Iterate over List of games and update the database
		for (Game game : listOfGames){
			gameRepository.save(game);
		}
        return listOfGames;
    }

    /**
     * This method updates our database with clips for particular Game.
     * @param game_id Every Game has its own game_id field.
     */
    @Async
    public void getAsyncClips(String game_id, Optional<String> fromDate) throws InterruptedException{
        
        clipRepository.deleteAll();
        
        LOG.info("Looking up clips {}", game_id);

        List<Clip> listOfClips = restTemplate.getForObject(TWITCH_API_BASE_URL +
        "/clips/?game_id=" + game_id + "&first=100", ClipsFeed.class).getData(); 
        
        // Iterate over list of clips and update the database
        for (Clip clip : listOfClips) {
            clipRepository.save(clip);
        }
        Thread.sleep(5000L);
    }
}

package dev.ivanlepi.twitchclips.service;

import java.util.List;
import dev.ivanlepi.twitchclips.models.Game;
import dev.ivanlepi.twitchclips.models.Feed;
import dev.ivanlepi.twitchclips.models.ClipsFeed;
import dev.ivanlepi.twitchclips.models.Clip;

import dev.ivanlepi.twitchclips.repository.GameRepository;




public class Twitch extends ApiBinding {

    private static final String TWITCH_API_BASE_URL = "https://api.twitch.tv/helix";

    private final GameRepository repository;

    public Twitch(String accessToken, GameRepository repository) {
        super(accessToken);
        this.repository = repository;
    }
 
    // Update DB with list of top 20 games from Twitch API
    public List<Game> updateGames() {
        List<Game> listOfGames = restTemplate.getForObject(TWITCH_API_BASE_URL +
        "/games/top", Feed.class).getData();
                
        // Empty the database to see if its updating properly
        repository.deleteAll();

        // Iterate over List of games and update the database
		for (Game game : listOfGames){
			repository.save(game);
		}
        // TODO Return status code and not list of games.
        return listOfGames;
    }

    // Update our database with clips for particular gameId
    public List<Clip> updateClips(String gameId) {
        return restTemplate.getForObject(TWITCH_API_BASE_URL + "/clips/?game_id=" + gameId, ClipsFeed.class).getData();
    }
}

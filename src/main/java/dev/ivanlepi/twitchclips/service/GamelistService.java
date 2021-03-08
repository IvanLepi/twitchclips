package dev.ivanlepi.twitchclips.service;

import dev.ivanlepi.twitchclips.models.Clip;
import dev.ivanlepi.twitchclips.models.Game;
import dev.ivanlepi.twitchclips.repository.GameRepository;
import dev.ivanlepi.twitchclips.repository.ClipsRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GamelistService {

	private final GameRepository gameRepository;
	private final ClipsRepository clipRepository;

	public static final Logger LOG = LoggerFactory.getLogger(GamelistService.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public GamelistService(GameRepository gameRepository, ClipsRepository clipRepository) {
		this.gameRepository = gameRepository;
		this.clipRepository = clipRepository;
	}

	/**
	 * This method returns the list of games from our database.
	 */
	public List<Game> getGames() {
		return gameRepository.findAll();
	}

	/**
	 * This method returns the list of Clips for selected game, selected broadcaster
	 * or both.
	 * 
	 * @param game_id        Every Clip object has game_id parameter.
	 * @param broadcaster_id Every Clip object has broadcaster_id parameter.
	 * @param page           Pagination.
	 * @return Page<Clip> This returns list of Clips.
	 */
	public Page<Clip> getClips(String game_id, String broadcaster_id, Pageable page, Sort sortBy) {
		return clipRepository.findBy(game_id, broadcaster_id, page, sortBy);
	}

	// Implementation of Pagination 2.0
	public Map<String, Object> getResponse(String game_id, String broadcaster_id, Pageable page, String sortBy)
			throws Exception {
		List<Clip> clips = new ArrayList<>();

		Page<Clip> pageClips;
		Map<String, Object> response = new HashMap<>();

		if (sortBy.equalsIgnoreCase("new")) {
			pageClips = getClips(game_id, broadcaster_id, page, Sort.by(Sort.Direction.DESC, "created_at"));
		} else if(sortBy.equalsIgnoreCase("trending")){
			clips = clipRepository.findTrendingClips(startDate(), endDate(), Sort.by(Sort.Direction.DESC, "view_count"));
			response.put("data", clips);
			pageClips = Page.empty();
		} else {
			pageClips = getClips(game_id, broadcaster_id, page, Sort.by(Sort.Direction.DESC, "view_count"));
		}


		if (pageClips.getTotalElements() > 0) {

			clips = pageClips.getContent();

			Map<String, Integer> pageData = new HashMap<String, Integer>();
			pageData.put("currentPage", pageClips.getNumber());
			pageData.put("totalItems", Math.toIntExact(pageClips.getTotalElements()));
			pageData.put("totalPages", pageClips.getTotalPages());

			response.put("data", clips);
			response.put("pagination", pageData);
		} else {
			response.put("data",clips);
		}

		return response;
	}

	// Calculate 24 hours earlier date string.
    private String startDate() {
        long currentDate = new Date().getTime();
        long newDate = currentDate - 86400000;
        String startDate = dateFormat.format(new Date(newDate));
        return startDate;
    }

	private String endDate(){
		long currentDate = new Date().getTime();
		long ltDate = currentDate + 86400000;
		String endDate = dateFormat.format(new Date(ltDate));
		return endDate;
	}
}

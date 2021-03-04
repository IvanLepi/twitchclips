package dev.ivanlepi.twitchclips.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.ivanlepi.twitchclips.repository.GameRepository;
import dev.ivanlepi.twitchclips.repository.ClipsRepository;
import dev.ivanlepi.twitchclips.service.GamelistService;

@Configuration
public class SocialConfig {

	private GameRepository gameRepo;
	private ClipsRepository clipRepo;

	@Bean
	public GamelistService gamelistService(GameRepository gameRepository, ClipsRepository clipRepository) {
		this.gameRepo = gameRepository;
		this.clipRepo = clipRepository;
		return new GamelistService(gameRepo, clipRepo);
	}
}

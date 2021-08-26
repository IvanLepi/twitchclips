package dev.ivanlepi.twitchclips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //TODO Include SecurityAutoConfiguration in prod.
public class TwitchClipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitchClipsApplication.class, args);
	}
}

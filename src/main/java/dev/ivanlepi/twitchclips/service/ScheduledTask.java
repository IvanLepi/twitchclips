package dev.ivanlepi.twitchclips.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Optional;

import dev.ivanlepi.twitchclips.models.Game;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final Twitch twitchService;

    public ScheduledTask(Twitch twitchService) {
        this.twitchService = twitchService;
    }

    // Update our database every 6 hours
    @Scheduled(cron = "0 */5 * ? * *")
    public void reportCurrentTime() {
        LOG.info("The time is now {}", dateFormat.format(new Date()));
        LOG.info("Running the update TASK");
        updateDb();
    }

    private void updateDb() {

        try {
            List<Game> listOfGames = twitchService.updateGames();

            // Kick of multiple, asynchronous lookups
            for (Game game : listOfGames) {
                twitchService.getAsyncClips(game.getId(), Optional.empty());
            }



        } catch (Exception e) {
            LOG.info("ERROR BRO", e.getMessage());
        }

    }
}

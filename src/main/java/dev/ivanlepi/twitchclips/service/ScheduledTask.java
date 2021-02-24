package dev.ivanlepi.twitchclips.service;

import java.text.SimpleDateFormat;
    import java.util.Date;

import java.util.List;
import java.util.Optional;

import com.mongodb.internal.thread.DaemonThreadFactory;

import dev.ivanlepi.twitchclips.models.Game;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    private final Twitch twitchService;

    public ScheduledTask(Twitch twitchService) {
        this.twitchService = twitchService;
    }

    // Update our database with Top Clips every day at 1 AM.
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateTopClips() {
        LOG.info("The time is now {}", dateFormat.format(new Date()));
        LOG.info("Updating Top Clips");
        updateDb(false);
    }

    // Update our database with Trending CLips every 3 hours.
    @Scheduled(cron = "0 0 */3 ? * *") // 0 */5 * ? * *
    public void updateTrendingClips() {
        LOG.info("The time is now {}", dateFormat.format(new Date()));
        LOG.info("Updating Trending Clips");

        updateDb(true);
    }


    private void updateDb(Boolean trending) {

        try {
            List<Game> listOfGames = twitchService.updateGames();

            // Kick of multiple, asynchronous lookups
            for (Game game : listOfGames) {
                if(!trending){
                    twitchService.getAsyncClips(game.getId(), Optional.empty());
                }else{
                    twitchService.getAsyncClips(game.getId(), Optional.of(startDate()));
                }
                
            }

        } catch (Exception e) {
            LOG.info("ERROR BRO", e.getMessage());
        }

    }

    //Calculate 24 hours earlier date.
    private String startDate() {
        long currentDate = new Date().getTime();
        long newDate = currentDate - 86400000;
        String startDate = dateFormat.format(new Date(newDate));
        return startDate.substring(0, (startDate.length() - 6)).concat("Z"); // change once we have different timezone
    }
}

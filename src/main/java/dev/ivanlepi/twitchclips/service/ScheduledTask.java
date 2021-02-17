package dev.ivanlepi.twitchclips.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import dev.ivanlepi.twitchclips.models.Clip;
import java.util.concurrent.CompletableFuture;
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
        this.twitchService =twitchService;
    }

    // Update our database every 6 hours 
	@Scheduled(cron="0 * * ? * *") // currently its every minute for testing purposes
	public void reportCurrentTime() {
		LOG.info("The time is now {}", dateFormat.format(new Date()));
        LOG.info("Running the update TASK");
        updateDb();
	}

    private void updateDb() {

        try{
            long start = System.currentTimeMillis();
            // Kick of multiple, asynchronous lookups
            CompletableFuture<List<Clip>> page1 = twitchService.getAsyncClips("516575");
            CompletableFuture<List<Clip>> page2 = twitchService.getAsyncClips("33214");
            CompletableFuture<List<Clip>> page3 = twitchService.getAsyncClips("460630");
    
            // Wait until they are all done
            CompletableFuture.allOf(page1,page2,page3).join();
    
            // Print results, including elapsed time
            LOG.info("Elapsed time: " + (System.currentTimeMillis() - start));
            LOG.info("--> " + page1.get().size());
            LOG.info("--> " + page2.get().size());
            LOG.info("--> " + page3.get().size());
        }catch (Exception e) {
            LOG.info("ERROR BRO", e.getMessage());
        }
        
    }
}

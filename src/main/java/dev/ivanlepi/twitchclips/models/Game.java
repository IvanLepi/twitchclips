package dev.ivanlepi.twitchclips.models;
import org.springframework.data.annotation.Id;

import lombok.Data;


@Data
public class Game {
    @Id String id;
    private String name;
    private String box_art_url;
}





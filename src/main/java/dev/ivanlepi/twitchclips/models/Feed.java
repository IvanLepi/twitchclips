package dev.ivanlepi.twitchclips.models;

import java.util.List;

import lombok.Data;

@Data
public class Feed {
    private List<Game> data;
}

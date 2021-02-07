package dev.ivanlepi.twitchclips.models;
import java.util.List;

import lombok.Data;

@Data
public class ClipsFeed {
    private List<Clip> data;
}

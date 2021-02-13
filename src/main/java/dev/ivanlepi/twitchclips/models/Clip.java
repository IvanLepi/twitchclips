package dev.ivanlepi.twitchclips.models;

import lombok.Data;

@Data

public class Clip {
    private String id;
    private String url;
    private String embed_url;
    private String broadcaster_id;
    private String broadcaster_name;
    private String creator_id;
    private String creator_name;
    private String video_id;
    private String game_id;
    private String language;
    private String title;
    private String view_count;
    private String created_at;
    private String thumbnail_url;

    public Clip(String game_id){
        this.game_id = game_id;
    }
}

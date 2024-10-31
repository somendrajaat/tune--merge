package com.tunemerge.tunemerge.Model.SpotifyModel;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
class Playlist {
    private boolean collaborative;
    private String description;
    private Map<String, String> external_urls;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private Owner owner;
    private String primary_color;
    private boolean isPublic;
    private String snapshot_id;
    private Tracks tracks;
    private String type;
    private String uri;

    // getters and setters
}
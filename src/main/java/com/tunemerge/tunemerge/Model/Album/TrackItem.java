package com.tunemerge.tunemerge.Model.Album;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
class TrackItem {
    private List<Artist> artists;
    private List<String> available_markets;
    private int disc_number;
    private int duration_ms;
    private boolean explicit;
    private Map<String, String> external_urls;
    private String href;
    private String id;
    private boolean is_local;
    private String name;
    private String preview_url;
    private int track_number;
    private String type;
    private String uri;
    // getters and setters
}
package com.tunemerge.tunemerge.Model.Album;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class AlbumResponse {
    private String album_type;
    private List<Artist> artists;
    private List<String> available_markets;
    private List<Copyright> copyrights;
    private Map<String, String> external_ids;
    private Map<String, String> external_urls;
    private List<String> genres;
    private String href;
    private String id;
    private List<Image> images;
    private String label;
    private String name;
    private int popularity;
    private String release_date;
    private String release_date_precision;
    private int total_tracks;
    private Tracks tracks;
    // getters and setters
}
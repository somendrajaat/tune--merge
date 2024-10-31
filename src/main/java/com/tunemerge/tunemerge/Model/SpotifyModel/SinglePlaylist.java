package com.tunemerge.tunemerge.Model.SpotifyModel;


import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SinglePlaylist {
    private boolean collaborative;
    private String description;
    private Followers followers;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private Owner owner;
    private String snapshot_id;
    private Tracks tracks;
    private String type;
    private String uri;
    private Map<String, String> external_urls;
    private boolean publicAccess;
}
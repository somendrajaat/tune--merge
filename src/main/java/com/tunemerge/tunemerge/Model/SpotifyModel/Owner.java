package com.tunemerge.tunemerge.Model.SpotifyModel;

import lombok.Data;

import java.util.Map;
@Data
class Owner {
    private String display_name;
    private Map<String, String> external_urls;
    private String href;
    private String id;
    private String type;
    private String uri;

    // getters and setters
}
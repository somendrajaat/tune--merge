package com.tunemerge.tunemerge.Model.Album;

import lombok.Data;

import java.util.Map;
@Data
class Artist {
    private Map<String, String> external_urls;
    private String href;
    private String id;
    private String name;
    private String type;
    private String uri;

    // getters and setters
}
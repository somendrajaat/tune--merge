package com.tunemerge.tunemerge.Model.userProfile;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserProfile {
    private String display_name;
    private Map<String, String> external_urls;
    private String href;
    private String id;
    private List<Image> images;
    private String type;
    private String uri;
    private Followers followers;

    // getters and setters
}
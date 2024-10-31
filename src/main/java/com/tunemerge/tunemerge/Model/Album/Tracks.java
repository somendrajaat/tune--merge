package com.tunemerge.tunemerge.Model.Album;

import lombok.Data;

import java.util.List;

@Data
class Tracks {
    private String href;
    private List<TrackItem> items;
    // getters and setters
}
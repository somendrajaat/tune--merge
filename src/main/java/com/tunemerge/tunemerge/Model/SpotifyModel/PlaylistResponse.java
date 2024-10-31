package com.tunemerge.tunemerge.Model.SpotifyModel;

import lombok.Data;

import java.util.List;
@Data
public class PlaylistResponse {
    private String href;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;
    private List<Playlist> items;

    // getters and setters
}
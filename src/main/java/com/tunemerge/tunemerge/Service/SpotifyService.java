package com.tunemerge.tunemerge.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tunemerge.tunemerge.Model.Album.AlbumResponse;
import com.tunemerge.tunemerge.Model.SpotifyModel.PlaylistResponse;
import com.tunemerge.tunemerge.Model.SpotifyModel.SinglePlaylist;
import com.tunemerge.tunemerge.Model.accessToken;
import com.tunemerge.tunemerge.Model.userProfile.UserProfile;
import com.tunemerge.tunemerge.Repository.AccessTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a service for interacting with the Spotify API.
 */
@Service
public class SpotifyService {

    private static final String SPOTIFY_API_URL = "https://api.spotify.com/v1/me/playlists";
    @Autowired
    AccessTokenRepository accessTRepository;
    public PlaylistResponse getUserPlaylists(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(SPOTIFY_API_URL, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String json = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(json, new TypeReference<PlaylistResponse>(){});
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }


    // ----------------------------------------------------------somendra---------------------------------------------------------------
    private static final String SPOTIFY_PROFILE_URL = "https://api.spotify.com/v1/me";

    public UserProfile getProfile(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(SPOTIFY_PROFILE_URL, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String json = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(json, UserProfile.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }



    public accessToken getAccessToken() {
        // bhai yeh access token jo database me save hoga udher se uthayega
        return accessTRepository.findTopByOrderByIdDesc();

    }



    public SinglePlaylist getPlaylistItems(String accessToken, String playlistId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://api.spotify.com/v1/playlists/" + playlistId + "/tracks";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String json = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                return objectMapper.readValue(json, SinglePlaylist.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }



    public AlbumResponse getAlbum(String accessToken, String albumId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "https://api.spotify.com/v1/albums/" + albumId;

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            String json = response.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {
                return objectMapper.readValue(json, new TypeReference<>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    public PlaylistResponse createPlaylist(String accessToken, String userId, String playlistName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        Map<String, Object> body = new HashMap<>();
        body.put("name", playlistName);
        body.put("description", "New playlist description");
        body.put("public", false);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<PlaylistResponse> response = restTemplate.postForEntity(
                "https://api.spotify.com/v1/users/" + userId + "/playlists",
                request,
                PlaylistResponse.class
        );
        return response.getBody();
    }
}

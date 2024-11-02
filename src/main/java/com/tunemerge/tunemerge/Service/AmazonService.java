package com.tunemerge.tunemerge.Service;

import com.tunemerge.tunemerge.Model.AmazonMusic.AmazonAccessToken;
import com.tunemerge.tunemerge.Repository.AmazonAccessTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service for interacting with the Amazon Music API.
 *
 * This service provides methods to retrieve tracks and access tokens from Amazon Music.
 *
 * @author Somendra
 */
@Service
public class AmazonService {
    @Autowired
    AmazonAccessTokenRepository accessTRepository;

    /**
     * Retrieves a specific track from Amazon Music.
     *
     * @param trackId the ID of the track to retrieve.
     * @return a String containing the track details.
     */
    public String getTrack(String trackId) {
        AmazonAccessToken token = getAccessToken();
        if (token == null || token.getAccessToken() == null) {
            return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.amazon.com/music/v1/tracks/" + trackId,
                String.class,
                entity
        );
        return response.getBody();
    }

    /**
     * Retrieves the latest access token from the database.
     *
     * @return the latest AmazonAccessToken.
     */
    public AmazonAccessToken getAccessToken() {
        return accessTRepository.findTopByOrderByIdDesc();
    }

    public String getPlaylist(String playlistId) {
        AmazonAccessToken token = getAccessToken();
        if (token == null || token.getAccessToken() == null) {
            return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.amazon.com/music/v1/playlists/" + playlistId,
                String.class,
                entity
        );
        return response.getBody();
    }
}
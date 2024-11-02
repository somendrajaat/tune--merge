package com.tunemerge.tunemerge.Service;

import com.tunemerge.tunemerge.Configuration.Config;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.tunemerge.tunemerge.Model.AmazonMusic.AmazonAccessToken;
import com.tunemerge.tunemerge.Model.SpotifyModel.accessToken;
import com.tunemerge.tunemerge.Repository.AccessTokenRepository;
import com.tunemerge.tunemerge.Repository.AmazonAccessTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Utility class for handling OAuth authentication with Spotify and Amazon Music.
 *
 * This class provides methods to generate authorization URLs and retrieve access tokens.
 *
 * @author Somendra
 */
@Service
public class OAuthUtil {

    private static final String CLIENT_ID = Config.getClientId();
    private static final String CLIENT_SECRET = Config.getClientSecret();
    private static final String AMAZON_CLIENT_ID = Config.getAmazonClientId();
    private static final String AMAZON_CLIENT_SECRET = Config.getAmazonClientSecret();
    private static final String REDIRECT_URI = "http://localhost:8080/tune_merge";

    @Autowired
    accessToken at;
    @Autowired
    AccessTokenRepository accessTokenRepository;
    @Autowired
    AmazonAccessTokenRepository amazonAccessTokenRepository;

    /**
     * Generates the authorization URL for Spotify OAuth authentication.
     *
     * @return The authorization URL.
     */
    public static String getAuthURL() {
        String scope = "playlist-read-public playlist-read-private";
        String authURL = "https://accounts.spotify.com/authorize?"
                + "client_id=" + CLIENT_ID
                + "&response_type=code"
                + "&redirect_uri=" + REDIRECT_URI;
        return authURL;
    }

    /**
     * Generates the authorization URL for Amazon OAuth authentication.
     *
     * @return The authorization URL.
     */
    public static String getAmazonAuthURL() {
        String scope = "catalog"; // Start with a basic scope
        String RedirectUri = "http://localhost:8080/tune_merge"; // Ensure this matches your registered URL

        try {
            return "https://www.amazon.com/ap/oa?"
                    + "client_id=" + URLEncoder.encode(AMAZON_CLIENT_ID, "UTF-8")
                    + "&scope=" + URLEncoder.encode(scope, "UTF-8")
                    + "&response_type=code"
                    + "&redirect_uri=" + URLEncoder.encode(RedirectUri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null; // Handle error gracefully
        }
    }

    /**
     * Retrieves the access token from Spotify using the provided authorization code.
     *
     * @param code The authorization code from Spotify.
     * @return The access token.
     */
    public accessToken getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", REDIRECT_URI);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<accessToken> response = restTemplate.postForEntity("https://accounts.spotify.com/api/token", request, accessToken.class);
        accessToken token = response.getBody();
        assert token != null;
        accessTokenRepository.save(token);
        return token;
    }

    /**
     * Retrieves the access token from Amazon Music using the provided authorization code.
     *
     * @param code The authorization code from Amazon Music.
     * @return The Amazon access token.
     */
    public AmazonAccessToken getAccessTokenAmazon(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Logger logger = LoggerFactory.getLogger(this.getClass());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(AMAZON_CLIENT_ID, AMAZON_CLIENT_SECRET);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", REDIRECT_URI);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<AmazonAccessToken> response = restTemplate.postForEntity("https://api.amazon.com/auth/o2/token", request, AmazonAccessToken.class);
        AmazonAccessToken token = response.getBody();
        assert token != null;
        amazonAccessTokenRepository.save(token);
        return token;
    }

    /**
     * Retrieves the access token from Amazon Music as a string using the provided authorization code.
     *
     * @param code The authorization code from Amazon Music.
     * @return The access token as a string.
     */
    public String getAccessTokenAmazonString(String code) {
        RestTemplate restTemplate = new RestTemplate();
        Logger logger = LoggerFactory.getLogger(this.getClass());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(AMAZON_CLIENT_ID, AMAZON_CLIENT_SECRET);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", REDIRECT_URI);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.amazon.com/auth/o2/token", request, String.class);
        String token = response.getBody();
        assert token != null;
        return token;
    }
}
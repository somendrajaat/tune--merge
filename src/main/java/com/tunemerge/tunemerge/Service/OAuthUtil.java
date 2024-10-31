/**
    * Generates the authorization URL for Spotify OAuth.
    * 
    * @return The authorization URL.
    */
package com.tunemerge.tunemerge.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.tunemerge.tunemerge.Model.accessToken;
import com.tunemerge.tunemerge.Repository.AccessTokenRepository;
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
 * Utility class for handling OAuth authentication with Spotify.
 */
@Service
public  class OAuthUtil {


    private static final String CLIENT_ID = "e80ca1772ab3487ca2092bd8de12fb35";
    private static final String AMAZON_CLIENT_ID = "amzn1.application-oa2-client.a70e6467740045d08b1ff319d6807473";
    private static final String CLIENT_SECRET = "7a43835ef73545258aef33809b98ff28";
    private static final String AMAZON_CLIENT_SECRET= "amzn1.oa2-cs.v1.29cd29344213e30041da040bf4e46d912d0aa9045e33e1fdab7837088e8d6123";
    private static final String REDIRECT_URI = "http://localhost:8080/tune_merge";
    
    /**
     * Generates the authorization URL for Spotify OAuth authentication.
     * 
     * @return The authorization URL.
     */
    public static String getAuthURL (){
        String scope="playlist-read-public playlist-read-private";
        String authURL="https://accounts.spotify.com/authorize?"
        +"client_id="+CLIENT_ID
        +"&response_type=code"
        +"&redirect_uri="+REDIRECT_URI;
        return authURL;
    }

    /**
     * Generates the authorization URL for Amazon OAuth authentication.
     *
     * @return The authorization URL.
     */
    public static String getAmazonAuthURL() {
        String scope = "music::catalog"; // Start with a basic scope
        String RedirectUri = "http://localhost:8080/amazonToken"; // Ensure this matches your registered URL

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
    @Autowired
    accessToken at;
    @Autowired
    AccessTokenRepository accessTokenRepository;



//------------------------------------------somendra---------------------------------------------------------------------------
public accessToken getAccessToken(String code) {


    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

    MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
    map.add("grant_type", "authorization_code");
    map.add("code", code);
    map.add("redirect_uri", REDIRECT_URI);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

    ResponseEntity<accessToken> response = restTemplate.postForEntity("https://accounts.spotify.com/api/token", request, accessToken.class);
    accessToken token= response.getBody();
    assert token != null;
    accessTokenRepository.save(token);


    return token;
}
public accessToken getAccessTokenAmazon(String code) {


        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", REDIRECT_URI);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<accessToken> response = restTemplate.postForEntity("https://api.amazon.com/auth/o2/token", request, accessToken.class);
        accessToken token= response.getBody();
        assert token != null;
        accessTokenRepository.save(token);


        return token;
    }
}

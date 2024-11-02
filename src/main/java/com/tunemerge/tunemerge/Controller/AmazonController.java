package com.tunemerge.tunemerge.Controller;

import com.tunemerge.tunemerge.Model.AmazonMusic.AmazonAccessToken;
import com.tunemerge.tunemerge.Service.AmazonService;
import com.tunemerge.tunemerge.Service.OAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for handling Amazon Music-related endpoints.
 *
 * @author Somendra
 */
@Controller
public class AmazonController {
    @Autowired
    OAuthUtil oAuthUtil;
    @Autowired
    AmazonService amazonService;

    /**
     * Redirects the user to the Amazon Music login page.
     *
     * @return a RedirectView to the Amazon Music login page.
     */
    @GetMapping("/loginAmazonMusic")
    public RedirectView login() {
        String oAuth = OAuthUtil.getAmazonAuthURL();
        return new RedirectView(oAuth);
    }

    /**
     * Handles the callback from Amazon Music with the authorization code and returns the access token.
     *
     * @param code the authorization code from Amazon Music.
     * @return a ResponseEntity containing the AmazonAccessToken.
     */
    @PostMapping("/amazonToken")
    public ResponseEntity<AmazonAccessToken> token(@RequestParam("code") String code) {
        AmazonAccessToken accessToken = oAuthUtil.getAccessTokenAmazon(code);
        return ResponseEntity.ok(accessToken);
    }

    /**
     * Handles the callback from Amazon Music with the authorization code and returns the access token as a string.
     *
     * @param code the authorization code from Amazon Music.
     * @return a ResponseEntity containing the access token as a string.
     */
    @PostMapping("/amazonTokenString")
    public ResponseEntity<String> tokenString(@RequestParam("code") String code) {
        String accessToken = oAuthUtil.getAccessTokenAmazonString(code);
        return ResponseEntity.ok(accessToken);
    }

    /**
     * Retrieves a specific track from Amazon Music.
     *
     * @param trackId the ID of the track to retrieve.
     * @return a String containing the track details.
     */
    @GetMapping("/amazonTrack")
    public String getTrack(@RequestParam String trackId) {
        return amazonService.getTrack(trackId);
    }
    @GetMapping("/amazonPlaylist")
    public String getPlaylist(@RequestParam String playlistId) {
        return amazonService.getPlaylist(playlistId);
    }
}
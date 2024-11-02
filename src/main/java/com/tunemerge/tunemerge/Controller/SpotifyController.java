package com.tunemerge.tunemerge.Controller;

import com.tunemerge.tunemerge.Model.Album.AlbumResponse;
import com.tunemerge.tunemerge.Model.SpotifyModel.PlaylistResponse;
import com.tunemerge.tunemerge.Model.SpotifyModel.SinglePlaylist;
import com.tunemerge.tunemerge.Model.SpotifyModel.accessToken;
import com.tunemerge.tunemerge.Service.OAuthUtil;
import com.tunemerge.tunemerge.Service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for handling Spotify-related endpoints.
 */
@RestController
public class SpotifyController {
    @Autowired
    SpotifyService spotifyService;
    @Autowired
    OAuthUtil oauthUtil;

    /**
     * Redirects the user to the Spotify login page.
     *
     * @return a RedirectView to the Spotify login page.
     */
    @GetMapping("/loginSpotify")
    public RedirectView login() {
        String authURL = OAuthUtil.getAuthURL();
        return new RedirectView(authURL);
    }

    /**
     * Handles the callback from Spotify with the authorization code.
     *
     * @param code the authorization code from Spotify.
     */
    @PostMapping("/tune_merge")
    public void token(@RequestParam("code") String code) {
        oauthUtil.getAccessToken(code);
    }

    /**
     * Retrieves the user's playlists from Spotify.
     *
     * @return a PlaylistResponse containing the user's playlists.
     */
    @GetMapping("/getplaylists")
    public PlaylistResponse getPlaylists() {
        accessToken token = spotifyService.getAccessToken();
        if (token == null || token.getAccess_token() == null) {
            return null;
        }
        return spotifyService.getUserPlaylists(token.getAccess_token());
    }

    /**
     * Retrieves a specific playlist from Spotify.
     *
     * @param id the ID of the playlist to retrieve.
     * @return a SinglePlaylist containing the playlist details.
     */
    @GetMapping("/getplaylist")
    public SinglePlaylist getPlaylist(@RequestParam String id) {
        accessToken token = spotifyService.getAccessToken();
        if (token == null || token.getAccess_token() == null) {
            return null;
        }
        return spotifyService.getPlaylistItems(token.getAccess_token(), id);
    }

    /**
     * Retrieves a specific album from Spotify.
     *
     * @param id the ID of the album to retrieve.
     * @return an AlbumResponse containing the album details.
     */
    @GetMapping("/getalbum")
    public AlbumResponse getAlbum(@RequestParam String id) {
        accessToken token = spotifyService.getAccessToken();
        if (token == null || token.getAccess_token() == null) {
            return null;
        }
        return spotifyService.getAlbum(token.getAccess_token(), id);
    }

    /**
     * Creates a new playlist on Spotify.
     *
     * @param userId the ID of the user for whom the playlist is being created.
     * @param playlistName the name of the new playlist.
     * @return a PlaylistResponse containing the details of the created playlist.
     */
    @PostMapping("/createPlaylist")
    public PlaylistResponse createSpotifyPlaylist(@RequestParam String userId, @RequestParam String playlistName) {
        accessToken token = spotifyService.getAccessToken();
        if (token == null || token.getAccess_token() == null) {
            return null;
        }
        return spotifyService.createPlaylist(token.getAccess_token(), userId, playlistName);
    }
}
package com.tunemerge.tunemerge.Controller;

import com.tunemerge.tunemerge.Model.Album.AlbumResponse;
import com.tunemerge.tunemerge.Model.SpotifyModel.PlaylistResponse;
import com.tunemerge.tunemerge.Model.SpotifyModel.SinglePlaylist;
import com.tunemerge.tunemerge.Model.accessToken;
import com.tunemerge.tunemerge.Model.userProfile.UserProfile;
import com.tunemerge.tunemerge.Service.OAuthUtil;
import com.tunemerge.tunemerge.Service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class SpotifyController {
    @Autowired
    SpotifyService spotifyService;
    @Autowired
    OAuthUtil oauthUtil;
    @GetMapping("/loginSpotify")
    public RedirectView login() {
        String authURL = OAuthUtil.getAuthURL();
        return new RedirectView(authURL);
    }
    @PostMapping("/tune_merge")
    public void token(@RequestParam("code") String code) {
        oauthUtil.getAccessToken(code);

    }
    @GetMapping("/getplaylists")
    public PlaylistResponse getPlaylists() {
        accessToken token = spotifyService.getAccessToken();
        if (token == null || token.getAccess_token() == null) {
            return null;
        }
        return spotifyService.getUserPlaylists(token.getAccess_token());
    }


    @GetMapping("/getplaylist")
    public SinglePlaylist getPlaylist(@RequestParam String id) {
        accessToken token = spotifyService.getAccessToken();
        if (token == null || token.getAccess_token() == null) {
            return null;
        }
        return spotifyService.getPlaylistItems(token.getAccess_token(),id);
    }

    @GetMapping("/getalbum")
    public AlbumResponse getAlbum(@RequestParam String id) {
        accessToken token = spotifyService.getAccessToken();
        if (token == null || token.getAccess_token() == null) {
            return null;
        }
        return spotifyService.getAlbum(token.getAccess_token(), id);
    }
    @PostMapping("/createPlaylist")
    public PlaylistResponse createSpotifyPlaylist(@RequestParam String userId, @RequestParam String playlistName) {
        accessToken token = spotifyService.getAccessToken();
        if (token == null || token.getAccess_token() == null) {
            return null;
        }
        return spotifyService.createPlaylist(token.getAccess_token(), userId, playlistName);
    }
}

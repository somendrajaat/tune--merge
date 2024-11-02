package com.tunemerge.tunemerge.Model.SpotifyModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Entity
@Repository
public class accessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String access_token;
    private String token_type;
    private String scope;
    private int expires_in;
    private String refresh_token;
}
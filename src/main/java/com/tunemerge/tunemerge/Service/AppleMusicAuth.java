//package com.tunemerge.tunemerge.Service;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//
//import java.security.Key;
//import java.util.Date;
//
//public class AppleMusicAuth {
//    private static final String TEAM_ID = "<Your Team ID>";
//    private static final String KEY_ID = "<Your Key ID>";
//    private static final String PRIVATE_KEY = "<Your Private Key>";
//
//    public static String generateDeveloperToken() {
//        Key key = Keys.hmacShaKeyFor(PRIVATE_KEY.getBytes());
//
//        String jws = Jwts.builder()
//                .setIssuer(TEAM_ID)
//                .setHeaderParam("kid", KEY_ID)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour expiration
//                .signWith(key, SignatureAlgorithm.ES256)
//                .compact();
//
//        return jws;
//    }
//}
package com.ennov.tickets.service.security.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.ennov.tickets.service.security.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {
    
    @Value("${david.app.jwtSecret}")
    private String jwtSecret;

    @Value("${david.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${david.app.jwtCookieName}")
    private String jwtCookie;

    public String getJwtFromCookies(HttpServletRequest request) {
        @SuppressWarnings("null")
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    @SuppressWarnings("null")
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
    String jwt = generateTokenFromUsername(userPrincipal.getUsername());
    ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
    return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        @SuppressWarnings("null")
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
        Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
        return true;
        } catch (MalformedJwtException e) {
            log.error("Token invalide", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Token expirer", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Token non supporter", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Token vide", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(String username) {   
    return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

}

package com.example.employee.util;

import com.example.employee.dto.UserEntityDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    private final String secret = "springbootjavapracticeatfrontendarmy";
    private final long expiryDuration = 5 * 60 * 60;

    public String generateJwt(UserEntityDTO userEntityDTO) {
        long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expiryDuration * 1000;
        Date issuedAt = new Date(milliTime);
        Date expiryAt = new Date(expiryTime);
        Claims claims = Jwts.claims()
                .setIssuer(userEntityDTO.getUserEmail())
                .setIssuedAt(issuedAt)
                .setExpiration(expiryAt);
        return Jwts.builder().setClaims(claims).setSubject(userEntityDTO.getUserEmail()).compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
            return Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token,String userName) {
        final String name = getUsernameFromToken(token);
        return (name.equals(userName) && !isTokenExpired(token));
    }

}

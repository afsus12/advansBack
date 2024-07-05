package com.example.finalbackendadvans.security.config;

import com.example.finalbackendadvans.entities.Role;
import io.jsonwebtoken.Jwts;

import  io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class JwtService {

     private static  final   String SECRET_KEY="F+ufpFGLDnFQUZ4ildAJs6B9g0H9eG7uVgOvDezBTCs=";
    public  String extractUsername(String token){
        return  extractClaim(token,Claims::getSubject);
    }
    public   Claims extractAllClaims(String token){

        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

    }
    public String generateToken(UserDetails userDetails){
        List<GrantedAuthority> auths = new ArrayList<>();
        for(GrantedAuthority role : userDetails.getAuthorities()){

            auths.add(new SimpleGrantedAuthority(role.getAuthority().toUpperCase()));
        }
        Map<String, Object> claims = new HashMap<>();
        System.out.println(auths);

        claims.put("roles", auths);
        System.out.println(claims);
        return  Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 60 * 24 * 7)).signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
return  Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+1000 * 60 * 60 * 24 * 7)).signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
    }
    public  boolean isTokenValid(String token , UserDetails userDetails){
        final  String  username=extractUsername(token);
        return  (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        Date now = new Date(System.currentTimeMillis() + 30000);

        return extractExpiration(token).before(now);
    }

    private Date extractExpiration(String token) {
        return  extractClaim(token,Claims::getExpiration);
    }

    public  <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final  Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private Key getSignInKey() {
        byte[]  keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

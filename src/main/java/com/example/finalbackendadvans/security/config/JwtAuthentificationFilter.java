package com.example.finalbackendadvans.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull  HttpServletResponse response,@NonNull  FilterChain filterChain) throws ServletException, IOException {
     final String authHeader = request.getHeader("Authorization");
     final  String jwt;
     final  String username;
     if( authHeader== null || !authHeader.startsWith("Bearer ")){
        filterChain.doFilter(request,response);
        return;
     }
     jwt =authHeader.substring(7);
        Claims claims = jwtService.extractAllClaims(jwt);
     username=claims.getSubject();
     if( username!= null && SecurityContextHolder.getContext().getAuthentication()==null){
         UserDetails userDetails= this.userDetailsService.loadUserByUsername(username);
         if(jwtService.isTokenValid(jwt,userDetails)){
             List<String> roles = ((List<?>) claims.get("roles")).stream()
                     .map(role -> (String) ((LinkedHashMap<?, ?>) role).get("authority"))
                     .collect(Collectors.toList());

         System.out.println(roles);

             UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                     userDetails,
                     null,
               userDetails.getAuthorities()


             );

             authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
             SecurityContextHolder.getContext().setAuthentication(authToken);
         }
     }
     filterChain.doFilter(request,response);
    }
}

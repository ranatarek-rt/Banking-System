package com.dragon.bankingSystem.security;

import com.dragon.bankingSystem.service.JWTService;
import com.dragon.bankingSystem.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    //inject the JwtService
    JWTService jwtService;

    //to get hold on the user details bean
    ApplicationContext applicationContext;

    @Autowired
    JwtFilter(JWTService jwtService,ApplicationContext applicationContext){
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //the token that well be sent will always start with
        // Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtb2FobWVkIiwiaWF0IjoxNzMwNzI3NzU3LCJleHAiOjE4NjkxODU5Nzg0OTc0NH0.A9SaGeqz2jPK6s6m7zR89MfFBF7JfAmFxdDgvr0V1zSZgOuOoHuKmV59wcRDpPNKPZfAePmo9Lia1KVCxN5oWg


        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            //to get the token without the Bearer space
            token = authHeader.substring(7);

            //extract the username from the token
            userName = jwtService.extractUserName(token);

            logger.debug("JWT Token: " + token);
            logger.debug("Extracted Username: " + userName);
           //logger.debug("Is Token Valid: " + jwtService.validateToken(token,userDetails));

        }

        //check if the username exist and check if he is not authorized
        if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null){
            //here we need to validate the token

            // this will get the username from the database
            UserDetails userDetails = applicationContext.getBean(UserDetailsServiceImpl.class).loadUserByUsername(userName);

            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // to change the user to authenticated user
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }
        filterChain.doFilter(request,response);
    }
}

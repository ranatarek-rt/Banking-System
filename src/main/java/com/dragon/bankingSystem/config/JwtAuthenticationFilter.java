package com.dragon.bankingSystem.config;
import com.dragon.bankingSystem.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component

// using the once per request filter to call the security for each new httpRequest
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    //inject the jwt service
    JwtAuthenticationFilter(JwtService jwtService,UserDetailsService userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;

    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        //to get the jwt passed in the header of each request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String email;

        //if there is no token based in the authorization or the header does not start with Bearer make an early return
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            doFilter(request,response,filterChain);
            return;
        }
        //get the jwt from the header

        jwt = authHeader.substring(7);

        //we need to call the user details service to check if the user with this token exits in the DB
        email = jwtService.extractUserEmailFromJwt(jwt);

        //if there is an email and the user is still not authenticated
        if(email!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtService.validateToken(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}

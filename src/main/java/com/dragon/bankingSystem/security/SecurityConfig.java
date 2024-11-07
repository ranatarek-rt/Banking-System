package com.dragon.bankingSystem.security;

import com.dragon.bankingSystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity

public class SecurityConfig  {

    private final JwtFilter jwtFilter;
    private final UserRepo userRepo;
    @Autowired
    public SecurityConfig(JwtFilter jwtFilter,UserRepo userRepo) {

        this.jwtFilter = jwtFilter;
        this.userRepo = userRepo;
    }

    UserDetailsService userDetailsService() {
        return username -> userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    // Encode the password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(config -> config
                        .requestMatchers(HttpMethod.POST, "/api/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/user/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/showLoginForm").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/showLoginForm?error=true").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/error").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/success").permitAll()
                        .requestMatchers("/resources/**", "/static/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form->form
                                .loginPage("/api/user/showLoginForm")  // Custom login page
                                .loginProcessingUrl("/api/user/login")
                                .defaultSuccessUrl("/api/user/success")// URL to handle the login POST
                                .failureHandler(authenticationFailureHandler())  // Custom failure handler
                                .permitAll()

                        )
                .exceptionHandling(ex-> ex.accessDeniedPage("/api/user/access"))

                // Remove formLogin configuration since you are handling login manually
                .logout(log -> log
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .httpBasic(basic -> basic
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Ensure stateless session for JWT
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Use jwtFilter for JWT handling

        return httpSecurity.build();
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/api/user/showLoginForm?error=true");
    }


    // DAO authentication will deal with the database
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Use the passwordEncoder bean
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    // This will talk to the authentication provider
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}

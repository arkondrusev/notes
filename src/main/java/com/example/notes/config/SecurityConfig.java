package com.example.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private static final String ROLE_READ = "ROLE_READ";
    private static final String ROLE_WRITE = "ROLE_WRITE";

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder)
                .withUser("reader")
                .password(passwordEncoder.encode("iread"))
                .authorities(ROLE_READ)
                .and()
                .withUser("writer")
                .password(passwordEncoder.encode("iwrite"))
                .authorities(ROLE_WRITE);
        return builder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(HttpMethod.PUT).hasAuthority(ROLE_WRITE)
                                .requestMatchers(HttpMethod.DELETE).hasAuthority(ROLE_WRITE)
                                .requestMatchers(HttpMethod.POST).hasAuthority(ROLE_WRITE)
                                .requestMatchers(HttpMethod.GET).hasAnyAuthority(ROLE_READ, ROLE_WRITE)
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

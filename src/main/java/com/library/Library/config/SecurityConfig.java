package com.library.Library.config;

import com.library.Library.service.userdetails.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/v3/api-docs/**",
                                "/swagger-ui/**", "/swagger-ui.html")
                        .permitAll()
                        .requestMatchers("/bookOffer/addBookOffer", "/bookOffer/getBookOfferByUserId", "/bookOffer/deleteBookOffer/**",
                                "/bookOffer/getBookOfferByUserId").hasRole("USER")
                        .requestMatchers("/bookOffer/list").hasRole("ADMIN")
                        .requestMatchers("/book/addBook", "/book/updateBook", "/book/deleteBook/**").hasRole("ADMIN")
                        .requestMatchers("/rating/updateRating", "/rating/deleteRating/**").hasRole("ADMIN")
                        .requestMatchers("/user/list").hasRole("ADMIN")
                        .requestMatchers("/bookOffer/updateBookOffer").hasAnyRole("ADMIN", "USER")
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}

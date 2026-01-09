package com.client.api.rasplus.config;

import com.client.api.rasplus.filter.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
                    try {
                        requests
                                .requestMatchers("/public/**").permitAll()
                                .anyRequest().authenticated()
                                //.and().csrf().disable()
                                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and().addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

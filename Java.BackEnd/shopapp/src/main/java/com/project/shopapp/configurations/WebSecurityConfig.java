package com.project.shopapp.configurations;

import com.project.shopapp.filters.JwtTokenFilter;
import com.project.shopapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(requests -> {
                requests.requestMatchers(
                    apiPrefix+"/users/login",
                    apiPrefix+"/users/register"
                    )
                    .permitAll()
                    
                    .requestMatchers(GET,
                        apiPrefix+"/categories**").hasAnyRole(Role.USER, Role.ADMIN)
                    .requestMatchers(POST,
                        apiPrefix+"/categories/**").hasAnyRole(Role.ADMIN)
                    .requestMatchers(PUT,
                        apiPrefix+"/categories/**").hasAnyRole(Role.ADMIN)
                    .requestMatchers(DELETE,
                        apiPrefix+"/categories/**").hasAnyRole(Role.ADMIN)
                    
                    .requestMatchers(GET,
                        apiPrefix+"/products**").hasAnyRole(Role.USER, Role.ADMIN)
                    .requestMatchers(POST,
                        apiPrefix+"/products/**").hasAnyRole(Role.ADMIN)
                    .requestMatchers(PUT,
                        apiPrefix+"/products/**").hasAnyRole(Role.ADMIN)
                    .requestMatchers(DELETE,
                        apiPrefix+"/products/**").hasAnyRole(Role.ADMIN)
                    
                    .requestMatchers(GET,
                        apiPrefix+"/orders/**").hasAnyRole(Role.USER,Role.ADMIN)
                    .requestMatchers(POST,
                        apiPrefix+"/orders/**").hasRole(Role.USER)
                    .requestMatchers(PUT,
                        apiPrefix+"/orders/**").hasRole(Role.ADMIN)
                    .requestMatchers(DELETE,
                        apiPrefix+"/orders/**").hasRole(Role.ADMIN)
                    
                    .requestMatchers(GET,
                        apiPrefix+"/order_details/**").hasAnyRole(Role.USER,Role.ADMIN)
                    .requestMatchers(POST,
                        apiPrefix+"/order_details/**").hasRole(Role.USER)
                    .requestMatchers(PUT,
                        apiPrefix+"/order_details/**").hasRole(Role.ADMIN)
                    .requestMatchers(DELETE,
                        apiPrefix+"/order_details/**").hasRole(Role.ADMIN)
                    
                    .anyRequest().authenticated();
            }).csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET","POST","PATCH","DELETE","OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization","content-type","x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**",configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return httpSecurity.build();
    }
}

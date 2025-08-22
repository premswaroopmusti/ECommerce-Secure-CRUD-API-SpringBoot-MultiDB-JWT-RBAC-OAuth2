package com.example.ecom_proj.config;

import com.example.ecom_proj.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        @Autowired
        MyUserDetailsService userDetailsService;

        @Autowired
        private com.example.ecom_proj.config.JwtFilter jwtFilter;

        @Value("${app.security.require-ssl:false}")
        private boolean requireSsl;

        /**
         * API Security – Stateless (JWT only)
         */
        @Bean
        @Order(1) // High priority for /api/** paths
        public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
                return http
                        .securityMatcher("/api/**") // apply only to API endpoints
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/auth/**").permitAll() // login/register API
                                .requestMatchers("/api/products").permitAll() //  allow products without JWT
                                .anyRequest().authenticated()
                        )
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
        }

        /**
         * Web Security – With sessions (OAuth2, form login)
         */
        @Bean
        @Order(2) // Lower priority, applies to everything else
        public SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
                if (requireSsl) {
                        http.requiresChannel(c -> c.anyRequest().requiresSecure())
                                .headers(h -> h.httpStrictTransportSecurity(hsts -> hsts
                                        .includeSubDomains(true)
                                        .preload(true)
                                        .maxAgeInSeconds(31536000))); // 1 year
                }

                return http
                        .csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/login", "/auth/login", "/register",
                                        "/login/google", "/login/github", "/oauth2/**")
                                .permitAll()
                                .anyRequest().authenticated()
                        )
                        .httpBasic(Customizer.withDefaults())
                        .oauth2Login(oauth2 -> oauth2
                                .defaultSuccessUrl("https://localhost:8443/api/products", true)
                        )
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                        .build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setPasswordEncoder(passwordEncoder());
                provider.setUserDetailsService(userDetailsService);
                return provider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(12);
        }
}

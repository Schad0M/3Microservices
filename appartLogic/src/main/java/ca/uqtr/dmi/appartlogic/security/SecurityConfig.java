package ca.uqtr.dmi.appartlogic.security;

import ca.uqtr.dmi.appartlogic.services.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
    /***
     * La Whitelist des URL
     */
    private static final String[] AUTH_WHITELIST = {
            "/auth/sign-up",
            "/auth/sign-in",
            "/auth/fruitsTestAuth",//Pour les tests auth
            "/appartements/retrieveAllAppart",
            "/appartements/retrieveAppartement/**",
            "/appartements/fruitsTestAppart"//Pour les tests appart
    };


    private final JwtAuthenticationEntryPoint jwtEntryPoint;
    private final JwtService jwtService;

    public SecurityConfig(JwtAuthenticationEntryPoint entryPoint, JwtService jwtService) {
        this.jwtEntryPoint = entryPoint;
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(ehc -> ehc.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> req
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated());
                        //.anyRequest().permitAll());

        //Ceci fait en sorte que les requetes passent par le filtre JwtAuthFilter
        http.addFilterBefore(new JwtAuthFilter(jwtService),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
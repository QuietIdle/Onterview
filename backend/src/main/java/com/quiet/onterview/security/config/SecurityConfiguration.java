package com.quiet.onterview.security.config;

import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.security.SecurityEntryPoint;
import com.quiet.onterview.security.jwt.JwtAuthenticationFilter;
import com.quiet.onterview.security.jwt.JwtAuthenticationManager;
import com.quiet.onterview.security.jwt.JwtDecoderFilter;
import com.quiet.onterview.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationManager authenticationManager;
    private final SecurityEntryPoint securityEntryPoint;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling.authenticationEntryPoint(securityEntryPoint))
                .addFilter(new CorsFilterConfiguration().corsFilter())
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider))
                .addFilterAfter(new JwtDecoderFilter(jwtTokenProvider, memberRepository),
                        UsernamePasswordAuthenticationFilter.class)
                .formLogin(configurer -> configurer.disable())
                .httpBasic(configurer -> configurer.disable())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/user/signup").permitAll()
                                .requestMatchers("/api/user/check/*").permitAll()
                                .anyRequest().authenticated());
        return http.build();
    }
}

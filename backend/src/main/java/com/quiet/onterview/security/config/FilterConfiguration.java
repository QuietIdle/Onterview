package com.quiet.onterview.security.config;

import com.quiet.onterview.common.JwtTokenProvider;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.security.jwt.JwtDecoderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfiguration {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Bean
    public FilterRegistrationBean<JwtDecoderFilter> registerJwtFilter() {
        FilterRegistrationBean<JwtDecoderFilter> registrationBean =
                new FilterRegistrationBean<>(new JwtDecoderFilter(jwtTokenProvider, memberRepository));
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(0);
        return registrationBean;
    }
}

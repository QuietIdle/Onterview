package com.quiet.onterview.security.jwt;


import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.security.SecurityMemberAuthentication;
import com.quiet.onterview.security.SecurityMemberDetail;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class JwtDecoderFilter implements Filter {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String ACCESS_TOKEN_HEADER = "AccessToken";
    private final String REFRESH_TOKEN_HEADER = "RefreshToken";
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String receivedToken = request.getHeader(AUTHORIZATION_HEADER);
        String receivedAccessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        String receivedRefreshToken = request.getHeader(REFRESH_TOKEN_HEADER);

        if(receivedAccessToken!=null && receivedRefreshToken!=null) {
            System.out.println("TOKEN REFRESH METHOD CALLED");
            filterChain.doFilter(request,response);
            return;
        }

        if(receivedToken==null) {
            System.out.println("NO AUTHORIZATION TOKEN");
            filterChain.doFilter(request, response);
            return;
        }

        boolean isValidUser = jwtTokenProvider.isValidToken(receivedToken);
        if(!isValidUser) {
            System.out.println("TOKEN EXPIRED AT FILTER");
            filterChain.doFilter(request, response);
            return;
        }

        String email = jwtTokenProvider.getEmail(receivedToken);
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("FILTER CANNOT FOUND USER"));
        SecurityContextHolder.getContext().setAuthentication(new SecurityMemberAuthentication(member));

        System.out.println("HOLDER VALUE - > " + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        filterChain.doFilter(request,response);
    }
}

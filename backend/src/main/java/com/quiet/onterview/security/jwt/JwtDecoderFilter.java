package com.quiet.onterview.security.jwt;


import com.quiet.onterview.common.ErrorCode;
import com.quiet.onterview.member.entity.Member;
import com.quiet.onterview.member.repository.MemberRepository;
import com.quiet.onterview.security.SecurityMemberAuthentication;
import com.quiet.onterview.security.SecurityUser;
import com.quiet.onterview.security.exception.SecurityException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class JwtDecoderFilter implements Filter {

    private final String AUTHORIZATION_HEADER = "Authorization";
    private final String ACCESS_TOKEN_HEADER = "AccessToken";
    private final String REFRESH_TOKEN_HEADER = "RefreshToken";
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String receivedToken = request.getHeader(AUTHORIZATION_HEADER);
        String receivedAccessToken = request.getHeader(ACCESS_TOKEN_HEADER);
        String receivedRefreshToken = request.getHeader(REFRESH_TOKEN_HEADER);

        if((receivedAccessToken!=null && receivedRefreshToken!=null)
                || (receivedToken==null)
                || (!jwtTokenProvider.isValidToken(receivedToken))) {
            filterChain.doFilter(request,response);
            return;
        }

        String email = jwtTokenProvider.getEmail(receivedToken);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new SecurityException(ErrorCode.EMAIL_NOT_EXISTS));
        SecurityContextHolder.getContext().setAuthentication(new SecurityMemberAuthentication(new SecurityUser(member)));

        filterChain.doFilter(request,response);
    }
}

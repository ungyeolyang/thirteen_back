package com.thirteen_back.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    private static final List<String> EXCLUDE_URLS = List.of(
            "/", "/auth/**", "/ws/**", "/travel/**","/api/**", "/elastic/**", "/favicon.ico","/manifest.json","/static/**","/img/**"
    );

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isExcludedUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return EXCLUDE_URLS.stream().anyMatch(excludeUrl -> {
            String pattern = excludeUrl.replace("**", ".*");
            return requestURI.matches(pattern);
        });
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isExcludedUrl(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = resolveToken(request);

        if (!StringUtils.hasText(jwt)) {
            log.warn("JWT 토큰이 요청 헤더에 존재하지 않습니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 필요합니다.");
            return;
        }

        try {
            if (tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.warn("유효하지 않은 JWT 토큰입니다.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.");
                return;
            }
        } catch (RuntimeException e) {
            log.error("JWT 인증 과정에서 에러 발생: ", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 인증 중 에러가 발생했습니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

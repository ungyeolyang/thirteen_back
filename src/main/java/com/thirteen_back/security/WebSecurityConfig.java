package com.thirteen_back.security;

import com.thirteen_back.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig implements WebMvcConfigurer {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // 인증 실패 시 처리할 클래스
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler; // 인가 실패 시 처리할 클래스
    @Bean // @Bean : 해당 메서드가 반환하는 객체가 Spring 컨테이너에 의해 관리하기 위함 // @Bean이 있는 메소드를 여러 번 호출하여도 항상 동일한 객체를 반환하여 싱글톤을 보장한다.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 암호화 객체를 Bean으로 등록
    }

    @Bean // SecurityFilterChain 객체를 Bean으로 등록
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { // HttpSecurity 객체를 통해 Spring Security 설정을 구성합니다.
        http
                .httpBasic() // HTTP 기본 인증을 활성화합니다.
                .and()
                .csrf().disable() // CSRF(Cross-Site Request Forgery) 보호를 비활성화합니다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 세션 관리 정책을 STATELESS로 설정하여 세션을 사용하지 않음을 명시합니다.
                .and()
                .exceptionHandling() // 인증 및 인가 에러 핸들링을 위한 설정을 추가합니다.
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authorizeRequests() // 요청에 대한 접근 권한을 설정합니다
                .antMatchers("/", "/auth/**", "/ws/**","/api/**", "/travel/**","/static/**").permitAll()
                // .antMatchers().permitAll(): 특정 경로에 대해 모든 사용자에게 접근 권한을 부여합니다.
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/favicon.ico","/manifest.json").permitAll()
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
        return http.build();
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5000", "https://seethestars.store")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
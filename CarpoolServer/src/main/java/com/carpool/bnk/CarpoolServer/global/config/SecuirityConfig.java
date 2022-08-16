package com.carpool.bnk.CarpoolServer.global.config;

import com.carpool.bnk.CarpoolServer.domain.user.service.UserService;
import com.carpool.bnk.CarpoolServer.global.auth.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 /**
     * 인증(authentication) 와 인가(authorization) 처리를 위한 스프링 시큐리티 설정 정의.
     */
@Configuration
@EnableWebSecurity    // @EnableWebSecurity 어노테이션을 달면SpringSecurityFilterChain이 자동으로 포함
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecuirityConfig extends WebSecurityConfigurerAdapter{


     @Autowired
     private UserService userService;

     @Bean
     public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
     }


     @Override   //HttpSecurity를 통해 HTTP 요청에 대한 웹기반 보안 구성
     protected void configure(HttpSecurity http) throws Exception {
         http
                 .httpBasic().disable()
                 .csrf().disable() //csrf(위조 사이트요청_보호기능을 disable, http Basic 사용하기 때문)
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 토큰 기반 인증이므로 세션 사용 하지않음
                 .and()
                 .addFilter(new JwtAuthenticationFilter(authenticationManager(),userService)) //HTTP 요청에 JWT 토큰 인증 필터를 거치도록 필터를 추가
                 .authorizeRequests()
                 .antMatchers("/api/user/login").permitAll() // 로그인 허용
                 .antMatchers("/api/user/signup").permitAll() // 회원가입 허용
                 .antMatchers("/api/user/idcheck").permitAll() // 회원가입 허용
                 .antMatchers("/api/carpool/create").permitAll() // 임시 카풀생성 허용!
                 .antMatchers("/api/**").authenticated()  // api 로 시작하는 URL 모두 JWT 필요
//                .anyRequest().permitAll() // Swagger사용을 위해 모든 URL 허용
                 .and().cors();
     }
}

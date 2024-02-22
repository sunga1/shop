package sun0aaa.s.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import sun0aaa.s.shop.handler.MyAccessDeniedHandler;
import sun0aaa.s.shop.handler.MyAuthenticationEntryPoint;
import sun0aaa.s.shop.handler.MyLoginSuccessHandler;
import sun0aaa.s.shop.handler.MyLogoutSuccessHandler;
import sun0aaa.s.shop.repository.MemberRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberRepository memberRepository;

    //로그인하지 않은 유저들만 접근 가능한 URL
    private static final String[] anonymousMemberUrl = {"/members/login","/members/join"};


    //로그인한 유저들만 접근 가능한 URL
    private static final String[] authenticatedMemberUrl = {"/members/myPage/**","/orders/**","/reviews/**/write","/qna/**/write","/orders/**"};


    //관리자만 접근 가능한 URL
    private static final String[] authenticatedAdminUrl = {"/admins/**","/items/create","/items/edit","/items/delete"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        return httpSecurity
                .csrf((csrfConfig)->
                        csrfConfig.disable()
                )
                .authorizeHttpRequests((authorizeRequests)->
                        authorizeRequests
                                .requestMatchers(anonymousMemberUrl).anonymous()
                                //authenticatedUserUrl에 대해서는 로그인을 요구
                                .requestMatchers(authenticatedMemberUrl).authenticated()
                                //해당 url에 대해서는 admin 권한을 갖고 있어야 접근 가능
                                .requestMatchers(authenticatedAdminUrl).hasAuthority("ADMIN")
                                //나머지 요청에 대해서는 로그인을 요구하지 않음
                                .anyRequest().permitAll()
                )
                .exceptionHandling((exceptionConfig)->
                        exceptionConfig
                                .accessDeniedHandler(new MyAccessDeniedHandler(memberRepository)) //인가 실패
                                .authenticationEntryPoint(new MyAuthenticationEntryPoint()) //인증 실패

                ) //로그인한 멤버만 접근할 수 있는 url에 로그인하지 않은 사용자가 접근할 경우
                .formLogin((formLogin)->
                        formLogin
                                //로그인 페이지를 제공하는 url을 설정
                                .loginPage("/members/login")
                                //로그인에 사용될 id
                                .usernameParameter("email")
                                //로그인에 사용될 password
                                .passwordParameter("password")
                                //로그인 성공시 실행될 handler
                                .successHandler(new MyLoginSuccessHandler(memberRepository))
                                //로그인 실패시 redirect 될 URL => 실패 메세지 출력
                                .failureUrl("/members/login?fail")

                )
                .logout((logoutConfig)->
                        logoutConfig
                                .logoutUrl("/members/logout")
                                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                )
                .build();


    }
}

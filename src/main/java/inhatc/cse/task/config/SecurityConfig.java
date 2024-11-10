package inhatc.cse.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.formLogin(form -> form
                .loginPage("/member/login")
                .defaultSuccessUrl("/")  // 로그인 후 리다이렉트 URL
                .failureUrl("/member/login/error")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
        );

        http.logout(Customizer.withDefaults());

        http.authorizeHttpRequests(request -> request
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/member/add").permitAll()
                .requestMatchers("/member/login").permitAll()
                .requestMatchers("/resume/**").authenticated()
                .requestMatchers("/profile/**").authenticated()
                .requestMatchers("/member/pwchange").authenticated()
                .anyRequest().authenticated()

        );

        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}

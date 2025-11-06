package br.com.fiap.visionhive.security;

import br.com.fiap.visionhive.config.CustomLoginSuccessHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class ApiSecurityConfig {

        @Bean
        public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
            http
                    .securityMatcher("/api/**")
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/comando-global-esp").permitAll()
                            .requestMatchers("/api/esp-status-report").permitAll()
                            .requestMatchers("/api/status").permitAll()
                            .anyRequest().hasAnyRole("ADMIN", "OPERADOR"));
            return http.build();
        }
    }

    @Configuration
    @Order(2)
    public static class WebSecurityConfig {

        @Bean
        public SecurityFilterChain webSecurity(HttpSecurity http, CustomLoginSuccessHandler successHandler) throws Exception {
            http
                    .securityMatcher("/**")
                    .csrf(csrf -> csrf
                            .csrfTokenRepository(new HttpSessionCsrfTokenRepository())
                            .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                    )
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/login", "/css/**", "/js/**", "/error", "/images/**").permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/profile/**").authenticated()
                            .anyRequest().authenticated()
                    )
                    .formLogin(form -> form
                            .loginPage("/login")
                            .successHandler(successHandler)
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login?logout")
                            .permitAll()
                    );

            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService) throws Exception {

            AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

            authBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder);

            return authBuilder.build();
        }
    }
}

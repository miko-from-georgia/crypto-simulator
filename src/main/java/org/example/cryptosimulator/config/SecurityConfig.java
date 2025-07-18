package org.example.cryptosimulator.config;

import org.example.cryptosimulator.repo.AuthorizationRepository;
import org.example.cryptosimulator.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/", "/login", "/register", "/error", "/css/**", "/img/**").permitAll().requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated()).formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").defaultSuccessUrl("/account", true).failureUrl("/login?error=true").permitAll()).logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").permitAll().permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(AuthorizationRepository authorizationRepository) {
        return new CustomUserDetailsService(authorizationRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

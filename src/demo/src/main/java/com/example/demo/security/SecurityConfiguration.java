package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private RepositoryUserDetailsService userDetailService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

  @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authenticationProvider(authenticationProvider());
    http
        .authorizeHttpRequests(authorize -> authorize
            // PUBLIC PAGES
            .requestMatchers("/", "/login", "/css/**", "/js/**", "/assets/**").permitAll()
            .requestMatchers("/registration_page", "/register", "/error", "/guest", "/home").permitAll()
            // PRIVATE PAGES
            .requestMatchers("/user_main_page", "/edit_user").hasRole("USER")
            .anyRequest().authenticated())
        .formLogin(formLogin -> formLogin
            .loginPage("/")
            .loginProcessingUrl("/login")
            .failureUrl("/")
            .defaultSuccessUrl("/home")
            .permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll())
        // Session Management to prevent session fixation and limit sessions
        .sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Session will be created only if required
            .invalidSessionUrl("/login") // Redirect to login if the session is invalid
            .maximumSessions(1) // Only allow one session per user
            .expiredUrl("/login") // Redirect to login if the session expires
            .maxSessionsPreventsLogin(true) // Prevent new login if the max sessions is reached
        );

    return http.build();
}

    
}

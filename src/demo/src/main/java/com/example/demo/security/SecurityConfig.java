package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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
                    // Static resources
                .requestMatchers( "/css/**", "/js/**", "/assets/**").permitAll()
                    // Website pages
                .requestMatchers("/", "/login", "/logout").permitAll()
                .requestMatchers("/registration_page", "/register", "/error", "/guest", "/home").permitAll()
                .requestMatchers("/communities", "communities/*").permitAll()
                .requestMatchers("/who_are_we").permitAll()
                .requestMatchers("/user/image/*").permitAll()
                 // PRIVATE PAGES
                .requestMatchers("/user_main_page", "/edit_user").hasRole("USER")
                .requestMatchers("/community/delete/*").hasRole("ADMIN")
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
                .permitAll());
                

        return http.build();
    }

}

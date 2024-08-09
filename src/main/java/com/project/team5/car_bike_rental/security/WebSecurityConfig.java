package com.project.team5.car_bike_rental.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/register", "/css/**", "/js/**", "/images/**", "/error", "/login", "/index", "/api/rentals").permitAll()
                        .requestMatchers("/bike/**", "/car/**", "/profile/**","/user/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)  // Redirect to home page after login
                .permitAll()
            )
            .logout(logout -> logout
            .logoutSuccessUrl("/")  // Redirect to index page after logout
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity; enable it in production with proper configuration
        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setUsersByUsernameQuery("select username, password, enabled from users where username=?");
        manager.setAuthoritiesByUsernameQuery("select u.username, r.role from users u join roles r on u.id = r.user_id where u.username=?");
        return manager;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

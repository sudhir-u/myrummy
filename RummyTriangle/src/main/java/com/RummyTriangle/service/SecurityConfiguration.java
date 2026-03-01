package com.RummyTriangle.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.authorizeRequests(auth -> auth
				.antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/user", "/liveusers", "/users/**", "/api/**", "/game/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/ws/**", "/topic/**", "/app/**").permitAll()
				.antMatchers("/", "/game.html", "/css/**", "/js/**").permitAll()
			)
			.formLogin(form -> form.defaultSuccessUrl("/user", true));
		return http.build();
	}

	@org.springframework.beans.factory.annotation.Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, UsersService usersService, PasswordEncoder passwordEncoder) throws Exception {
		auth.userDetailsService(usersService).passwordEncoder(passwordEncoder);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Collections.singletonList("*"));  // Restrict in production
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		config.setAllowCredentials(false);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}

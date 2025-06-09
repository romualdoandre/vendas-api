package com.romualdoandre.vendasapi.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.romualdoandre.vendasapi.JwtTokenFilter;
import com.romualdoandre.vendasapi.service.JwtService;
import com.romualdoandre.vendasapi.service.impl.SecurityUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	@Autowired
	private SecurityUserDetailsService userDetailsService;
	@Autowired
	private JwtService jwtService;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->{
        	csrf.disable();
        })
        .authorizeHttpRequests((authz) -> authz
        		.requestMatchers(HttpMethod.POST, "/api/usuarios/autenticar").permitAll()
        		.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
        		.requestMatchers(HttpMethod.GET, "/api/dashboard").permitAll()
                .anyRequest().authenticated()
            )
        .sessionManagement((sess)->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	
	/*@Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password("password")
            .roles("USER")
            .passwordEncoder(passwordEncoder()::encode)
            .build();
        return new userdetail
    }*/
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		return new JwtTokenFilter(jwtService, userDetailsService);
	}
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		var all = List.of("*");
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedMethods(all);
		config.setAllowedOriginPatterns(all);
		config.setAllowedHeaders(all);
		config.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		CorsFilter filter = new CorsFilter(source);
		FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(filter);
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registration;
	}
}

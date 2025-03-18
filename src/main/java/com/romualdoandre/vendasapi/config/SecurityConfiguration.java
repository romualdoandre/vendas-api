package com.romualdoandre.vendasapi.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.romualdoandre.vendasapi.service.impl.SecurityUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	//@Autowired
	//private SecurityUserDetailsService userDetailsService;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->{
        	csrf.disable();
        })
        .authorizeHttpRequests((authz) -> authz
        		.requestMatchers(HttpMethod.POST, "/api/usuarios/autenticar").permitAll()
        		.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                .anyRequest().authenticated()
            )
        .sessionManagement((sess)->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(withDefaults());
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
}

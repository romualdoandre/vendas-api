package com.romualdoandre.vendasapi;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.romualdoandre.vendasapi.service.JwtService;
import com.romualdoandre.vendasapi.service.impl.SecurityUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter{

	private JwtService jwtService;
	private SecurityUserDetailsService detailsService;

	public JwtTokenFilter(JwtService jwtService, SecurityUserDetailsService detailsService) {
		this.jwtService = jwtService;
		this.detailsService = detailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		if(authorization != null && authorization.startsWith("Bearer")) {
			String token = authorization.split(" ")[1];
			boolean tokenValido = jwtService.isTokenValido(token);
			if(tokenValido) {
				String login = jwtService.obterLoginUsuario(token);
				UserDetails userDetails = detailsService.loadUserByUsername(login);
				UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userToken);
				
			}
		}
		filterChain.doFilter(request, response);
	}

}

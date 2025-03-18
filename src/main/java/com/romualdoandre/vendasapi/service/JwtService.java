package com.romualdoandre.vendasapi.service;

import com.romualdoandre.vendasapi.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public interface JwtService {
	
	String gerarToken(Usuario usuario);
	Claims obterClaims(String token) throws ExpiredJwtException;
	boolean isTokenValido(String token);
	String obterLoginUsuario(String token);
}

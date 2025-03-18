package com.romualdoandre.vendasapi.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import com.romualdoandre.vendasapi.model.Usuario;
import com.romualdoandre.vendasapi.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtServiceImpl implements JwtService {
	@Value("${jwt.expiracao}")
	String expiracao;
	@Value("${jwt.chave-assinatura}")
	String chaveAssinatura;

	@Override
	public String gerarToken(Usuario usuario) {
		long expLong = Long.valueOf(expiracao);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expLong);
		java.util.Date data = java.util.Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
		SecretKey key = Keys.hmacShaKeyFor(chaveAssinatura.getBytes());
		String horaExpiracao = DateTimeFormatter.ofPattern("HH:mm").format(dataHoraExpiracao);
		String token = Jwts.builder()
				.setExpiration(data)
				.setSubject(usuario.getEmail())
				.claim("nome", usuario.getNome())
				.claim("horaExpiracao", horaExpiracao)
				.signWith(key)
				.compact();
		return token;
	}

	@Override
	public Claims obterClaims(String token) throws ExpiredJwtException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTokenValido(String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String obterLoginUsuario(String token) {
		// TODO Auto-generated method stub
		return null;
	}

}

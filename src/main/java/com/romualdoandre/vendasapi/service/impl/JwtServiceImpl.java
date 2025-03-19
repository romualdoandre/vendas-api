package com.romualdoandre.vendasapi.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.romualdoandre.vendasapi.model.Usuario;
import com.romualdoandre.vendasapi.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
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
		return Jwts.parser()
				.setSigningKey(chaveAssinatura)
				.parseClaimsJws(token)
				.getBody();
	}

	@Override
	public boolean isTokenValido(String token) {
		try {
			Claims claims = this.obterClaims(token);
			Date dataExp = claims.getExpiration();
			LocalDateTime dateTimeExp = dataExp.toInstant()
					.atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(dateTimeExp);
		}
		catch(ExpiredJwtException ex) {
			return false;
		}
	}

	@Override
	public String obterLoginUsuario(String token) {
		Claims claims = obterClaims(token);
		String login = claims.getSubject();
		return login;
	}

}

package com.romualdoandre.vendasapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.romualdoandre.vendasapi.model.Usuario;
import com.romualdoandre.vendasapi.model.repository.UsuarioRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
 		Usuario usuario = repository.findByEmail(email)
 				.orElseThrow(()->new UsernameNotFoundException("Usuário não encontrado"));

		return User.builder()
				.username(usuario.getEmail())
				.password(usuario.getSenha())
				.roles("USER").build();
	}

}

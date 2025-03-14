package com.romualdoandre.vendasapi.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.romualdoandre.vendasapi.exception.ErroAutenticacao;
import com.romualdoandre.vendasapi.exception.RegraNegocioException;
import com.romualdoandre.vendasapi.model.Usuario;
import com.romualdoandre.vendasapi.model.repository.UsuarioRepository;
import com.romualdoandre.vendasapi.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private UsuarioRepository repository;
	private PasswordEncoder passwordEncoder;
	
	
	public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
		super();
		this.repository=repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
Optional<Usuario> usuario = repository.findByEmail(email);
		
		if(!usuario.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
		}
		
		boolean senhasBatem = passwordEncoder.matches(senha, usuario.get().getSenha());
		
		if(!senhasBatem) {
			throw new ErroAutenticacao("Senha inválida.");
		}

		return usuario.get();
	}

	@Override
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		criptografarSenha(usuario);
		return repository.save(usuario);
	}
	
	private void criptografarSenha(Usuario usuario) {
		String senha = usuario.getSenha();
		String senhaCripto = passwordEncoder.encode(senha);
		usuario.setSenha(senhaCripto);
	}

	@Override
	public void validarEmail(String email) throws RegraNegocioException {
		boolean existe= repository.existsByEmail(email);
		if(existe)
			throw new RegraNegocioException("Já existe usuário com este e-mail");
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}

}

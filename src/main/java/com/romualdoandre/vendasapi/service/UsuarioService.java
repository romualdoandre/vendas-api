package com.romualdoandre.vendasapi.service;

import java.util.Optional;

import com.romualdoandre.vendasapi.exception.RegraNegocioException;
import com.romualdoandre.vendasapi.model.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email) throws RegraNegocioException;
	
	Optional<Usuario> obterPorId(Long id);
	
}

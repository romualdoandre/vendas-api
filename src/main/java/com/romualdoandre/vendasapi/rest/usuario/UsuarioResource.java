package com.romualdoandre.vendasapi.rest.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romualdoandre.vendasapi.dto.TokenDTO;
import com.romualdoandre.vendasapi.dto.UsuarioDTO;
import com.romualdoandre.vendasapi.exception.ErroAutenticacao;
import com.romualdoandre.vendasapi.exception.RegraNegocioException;
import com.romualdoandre.vendasapi.model.Usuario;
import com.romualdoandre.vendasapi.service.JwtService;
import com.romualdoandre.vendasapi.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {
	
	private final UsuarioService service;
	private final JwtService jwtService;
	
	public UsuarioResource(UsuarioService service, JwtService jwtService) {
		this.service=service;
		this.jwtService=jwtService;
	}
	
	@PostMapping
	public ResponseEntity<?> salvar( @RequestBody UsuarioDTO dto ) {
		
		Usuario usuario = Usuario.builder()
					.nome(dto.getNome())
					.email(dto.getEmail())
					.senha(dto.getSenha()).build();
		
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar( @RequestBody UsuarioDTO dto ) {
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			String token = jwtService.gerarToken(usuarioAutenticado);
			TokenDTO tokenDTO = new TokenDTO( usuarioAutenticado.getNome(), token);
			return ResponseEntity.ok(tokenDTO);
		}catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}

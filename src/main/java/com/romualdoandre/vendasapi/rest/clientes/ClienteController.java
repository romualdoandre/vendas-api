package com.romualdoandre.vendasapi.rest.clientes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romualdoandre.vendasapi.model.Cliente;
import com.romualdoandre.vendasapi.model.Produto;
import com.romualdoandre.vendasapi.model.repository.ClienteRepository;
import com.romualdoandre.vendasapi.rest.produtos.ProdutoFormRequest;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;
	
	@PostMapping
	public ResponseEntity<ClienteFormRequest> salvar(@RequestBody ClienteFormRequest cliente) {
		Cliente clienteEntidade = cliente.toModel();
		repository.save(clienteEntidade);
		return ResponseEntity.ok(ClienteFormRequest.fromModel(clienteEntidade));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody ClienteFormRequest cliente) {
		Optional<Cliente> clienteExistente = repository.findById(id);
		if(clienteExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cliente entidade = cliente.toModel();
		entidade.setId(id);
		repository.save(entidade);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ClienteFormRequest> getById(@PathVariable Long id) {
		Optional<Cliente> clienteExistente = repository.findById(id);
		
		return clienteExistente.map(ClienteFormRequest::fromModel)
				.map(clienteFR -> ResponseEntity.ok(clienteFR))
				.orElseGet( () -> ResponseEntity.notFound().build());
		
	}
	
}

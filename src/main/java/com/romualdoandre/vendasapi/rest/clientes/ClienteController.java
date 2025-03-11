package com.romualdoandre.vendasapi.rest.clientes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.romualdoandre.vendasapi.model.Cliente;
import com.romualdoandre.vendasapi.model.repository.ClienteRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("*")
public class ClienteController {

	@Autowired
	private ClienteRepository repository;
	
	@Operation(summary = "Salvar novo cliente")
	@PostMapping
	public ResponseEntity<ClienteFormRequest> salvar(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
				    description = "Informações do cliente", required = true,
				    content = @Content(mediaType = "application/json",
				      schema = @Schema(implementation = ClienteFormRequest.class),
				      examples = @ExampleObject(value = "{\r\n"
				      		+ "  \"id\": 0,\r\n"
				      		+ "  \"nascimento\": \"2025-03-11\",\r\n"
				      		+ "  \"cpf\": \"string\",\r\n"
				      		+ "  \"nome\": \"string\",\r\n"
				      		+ "  \"endereco\": \"string\",\r\n"
				      		+ "  \"telefone\": \"string\",\r\n"
				      		+ "  \"email\": \"string\",\r\n"
				      		+ "  \"cadastro\": \"2025-03-11\",\r\n"
				      		+ "  \"dataCadastro\": \"2025-03-11\"\r\n"
				      		+ "}")))
			@RequestBody ClienteFormRequest cliente) {
		Cliente clienteEntidade = cliente.toModel();
		repository.save(clienteEntidade);
		return ResponseEntity.ok(ClienteFormRequest.fromModel(clienteEntidade));
	}
	@Operation(summary = "Atualizar cliente")
	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(@Parameter(description = "id do cliente")  @PathVariable Long id,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
				    description = "Informações do cliente", required = true,
				    content = @Content(mediaType = "application/json",
				      schema = @Schema(implementation = ClienteFormRequest.class),
				      examples = @ExampleObject(value = "{\r\n"
				      		+ "  \"id\": 0,\r\n"
				      		+ "  \"nascimento\": \"2025-03-11\",\r\n"
				      		+ "  \"cpf\": \"string\",\r\n"
				      		+ "  \"nome\": \"string\",\r\n"
				      		+ "  \"endereco\": \"string\",\r\n"
				      		+ "  \"telefone\": \"string\",\r\n"
				      		+ "  \"email\": \"string\",\r\n"
				      		+ "  \"cadastro\": \"2025-03-11\",\r\n"
				      		+ "  \"dataCadastro\": \"2025-03-11\"\r\n"
				      		+ "}")))
			@RequestBody ClienteFormRequest cliente) {
		Optional<Cliente> clienteExistente = repository.findById(id);
		if(clienteExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cliente entidade = cliente.toModel();
		entidade.setId(id);
		repository.save(entidade);
		return ResponseEntity.ok().build();
	}
	@Operation(summary = "Consultar cliente pelo id")
	@ApiResponses(value = { 
			  @ApiResponse(responseCode = "200", description = "Cliente encontrado", 
			    content = { @Content(mediaType = "application/json", 
			      schema = @Schema(implementation = ClienteFormRequest.class)) }),
			  @ApiResponse(responseCode = "404", description = "Cliente não encontrado", 
			    content = @Content) })
	@GetMapping("{id}")
	public ResponseEntity<ClienteFormRequest> getById(@Parameter(description="id do cliente") @PathVariable Long id) {
		Optional<Cliente> clienteExistente = repository.findById(id);
		
		return clienteExistente.map(ClienteFormRequest::fromModel)
				.map(clienteFR -> ResponseEntity.ok(clienteFR))
				.orElseGet( () -> ResponseEntity.notFound().build());
		
	}

	@Operation(summary = "Pesquisar clientes pelo nome ou cpf")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente encontrado",
			content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Page.class), examples = @ExampleObject(value="{\r\n"
					+ "  \"content\": [\r\n"
					+ "    {\r\n"
					+ "      \"id\": 27,\r\n"
					+ "      \"nascimento\": \"30/01/2025\",\r\n"
					+ "      \"cpf\": \"123.123.123-02\",\r\n"
					+ "      \"nome\": \"John Doe2\",\r\n"
					+ "      \"endereco\": \"Rua 2\",\r\n"
					+ "      \"telefone\": \"(12)31231-2302\",\r\n"
					+ "      \"email\": \"email2@email.com\",\r\n"
					+ "      \"cadastro\": \"30/01/2025\",\r\n"
					+ "      \"dataCadastro\": \"2025-01-30\"\r\n"
					+ "    },\r\n"
					+ "    {\r\n"
					+ "      \"id\": 37,\r\n"
					+ "      \"nascimento\": \"30/01/2025\",\r\n"
					+ "      \"cpf\": \"123.123.123-12\",\r\n"
					+ "      \"nome\": \"John Doe12\",\r\n"
					+ "      \"endereco\": \"Rua 12\",\r\n"
					+ "      \"telefone\": \"(12)31231-2312\",\r\n"
					+ "      \"email\": \"email12@email.com\",\r\n"
					+ "      \"cadastro\": \"30/01/2025\",\r\n"
					+ "      \"dataCadastro\": \"2025-01-30\"\r\n"
					+ "    },\r\n"
					+ "    {\r\n"
					+ "      \"id\": 45,\r\n"
					+ "      \"nascimento\": \"30/01/2025\",\r\n"
					+ "      \"cpf\": \"123.123.123-20\",\r\n"
					+ "      \"nome\": \"John Doe20\",\r\n"
					+ "      \"endereco\": \"Rua 20\",\r\n"
					+ "      \"telefone\": \"(12)31231-2320\",\r\n"
					+ "      \"email\": \"email20@email.com\",\r\n"
					+ "      \"cadastro\": \"30/01/2025\",\r\n"
					+ "      \"dataCadastro\": \"2025-01-30\"\r\n"
					+ "    }\r\n"
					+ "  ],\r\n"
					+ "  \"pageable\": {\r\n"
					+ "    \"pageNumber\": 0,\r\n"
					+ "    \"pageSize\": 10,\r\n"
					+ "    \"sort\": {\r\n"
					+ "      \"sorted\": false,\r\n"
					+ "      \"unsorted\": true,\r\n"
					+ "      \"empty\": true\r\n"
					+ "    },\r\n"
					+ "    \"offset\": 0,\r\n"
					+ "    \"paged\": true,\r\n"
					+ "    \"unpaged\": false\r\n"
					+ "  },\r\n"
					+ "  \"last\": true,\r\n"
					+ "  \"totalElements\": 3,\r\n"
					+ "  \"totalPages\": 1,\r\n"
					+ "  \"sort\": {\r\n"
					+ "    \"sorted\": false,\r\n"
					+ "    \"unsorted\": true,\r\n"
					+ "    \"empty\": true\r\n"
					+ "  },\r\n"
					+ "  \"first\": true,\r\n"
					+ "  \"numberOfElements\": 3,\r\n"
					+ "  \"size\": 10,\r\n"
					+ "  \"number\": 0,\r\n"
					+ "  \"empty\": false\r\n"
					+ "}"))}
	) 
	})
	@GetMapping
	public Page<ClienteFormRequest> getLista(
			@Parameter(description="nome do cliente")
		@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
		@Parameter(description="cpf do cliente")
		@RequestParam(value = "cpf", required = false, defaultValue = "") String cpf,
		@Parameter(description="dados de paginação")
		Pageable pageable
	){
		return repository
				.buscarPorNomeCpf("%" + nome + "%", "%" + cpf+ "%", pageable)
				.map( ClienteFormRequest::fromModel  );
				
	}
	@Operation(summary = "Apagar um cliente pelo id")
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletar(@Parameter(description = "id do cliente") @PathVariable Long id){
		Optional<Cliente> clienteExistente = repository.findById(id);
		if(clienteExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Cliente entidade = clienteExistente.get();
		entidade.setId(id);
		repository.delete(entidade);
		return ResponseEntity.noContent().build();
	}
}

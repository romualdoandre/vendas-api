package com.romualdoandre.vendasapi.rest.produtos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romualdoandre.vendasapi.model.Produto;
import com.romualdoandre.vendasapi.model.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin("*")
public class ProdutoController {
	@Autowired
	private ProdutoRepository repository;

	@PostMapping
	public ProdutoFormRequest salvar(@RequestBody ProdutoFormRequest produto) {
		
		Produto entidadeProduto = produto.toModel();
		repository.save(entidadeProduto);
		produto.setId(entidadeProduto.getId());
		return ProdutoFormRequest.fromModel(entidadeProduto);
	}
	@PutMapping("{id}")
	public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody ProdutoFormRequest produto) {
		Optional<Produto> produtoExistente = repository.findById(id);
		if(produtoExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Produto entidade = produto.toModel();
		entidade.setId(id);
		repository.save(entidade);
		return ResponseEntity.ok().build();
	}
	@GetMapping
	public List<ProdutoFormRequest> list(){
		return repository.findAll().stream().map(ProdutoFormRequest::fromModel).toList();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ProdutoFormRequest> getById(@PathVariable Long id) {
		Optional<Produto> produtoExistente = repository.findById(id);
		if(produtoExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoExistente.map(ProdutoFormRequest::fromModel).get());
	}
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		Optional<Produto> produtoExistente = repository.findById(id);
		if(produtoExistente.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Produto entidade = produtoExistente.get();
		entidade.setId(id);
		repository.delete(entidade);
		return ResponseEntity.noContent().build();
	}
}

package com.romualdoandre.vendasapi.rest.vendas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romualdoandre.vendasapi.model.Venda;
import com.romualdoandre.vendasapi.model.repository.ItemVendaRepository;
import com.romualdoandre.vendasapi.model.repository.VendasRepository;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin("*")
public class VendasController {

	@Autowired
	private VendasRepository repository;
	@Autowired
	private ItemVendaRepository itemVendaRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> realizarVenda(@RequestBody Venda venda) {
		repository.save(venda);
		venda.getItens().forEach(iv->iv.setVenda(venda));
		itemVendaRepository.saveAll(venda.getItens());
		return ResponseEntity.ok().build();
	}
}

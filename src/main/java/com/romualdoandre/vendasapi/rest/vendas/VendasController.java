package com.romualdoandre.vendasapi.rest.vendas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romualdoandre.vendasapi.model.Venda;
import com.romualdoandre.vendasapi.model.repository.ItemVendaRepository;
import com.romualdoandre.vendasapi.model.repository.VendasRepository;
import com.romualdoandre.vendasapi.service.RelatorioVendasService;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin("*")
public class VendasController {

	@Autowired
	private VendasRepository repository;
	@Autowired
	private ItemVendaRepository itemVendaRepository;
	@Autowired
	private RelatorioVendasService service;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Void> realizarVenda(@RequestBody Venda venda) {
		repository.save(venda);
		venda.getItens().forEach(iv->iv.setVenda(venda));
		itemVendaRepository.saveAll(venda.getItens());
		return ResponseEntity.ok().build();
	}
	@GetMapping("/relatorio-vendas")
	public ResponseEntity<byte[]> relatorioVendas(){
		HttpHeaders header = new HttpHeaders();
		String fileName = "relatorio-vendas.pdf";
		header.setContentDispositionFormData("inline; filename=\""+fileName+"\"", fileName);
		header.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(service.gerarRelatorioCompiladoFacil(),header,HttpStatus.OK);
		return response;
	}
}

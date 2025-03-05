package com.romualdoandre.vendasapi.rest.vendas;

import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.romualdoandre.vendasapi.model.Venda;
import com.romualdoandre.vendasapi.model.repository.ItemVendaRepository;
import com.romualdoandre.vendasapi.model.repository.VendasRepository;
import com.romualdoandre.vendasapi.service.RelatorioVendasService;
import com.romualdoandre.vendasapi.utils.DateUtils;

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
	public ResponseEntity<byte[]> relatorioVendas(
			@RequestParam(value="id", required=false) Long id,
			@RequestParam(value="inicio", required=false, defaultValue="") String inicio,
			@RequestParam(value="fim", required=false, defaultValue="") String fim){
		
		Date dataInicio = DateUtils.fromString(inicio, false);
		Date dataFim = DateUtils.fromString(fim, true);
		
		if(dataInicio == null) {
			dataInicio = DateUtils.DATA_INICIO_PADRAO;
		}
		
		if(dataFim == null) {
			dataFim = DateUtils.hoje(true);
		}
		
		HttpHeaders header = new HttpHeaders();
		String fileName = "relatorio-vendas.pdf";
		header.setContentDispositionFormData("inline; filename=\""+fileName+"\"", fileName);
		header.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(service.gerarRelatorioCompiladoFacil(id, dataInicio, dataFim),header,HttpStatus.OK);
		return response;
	}
}

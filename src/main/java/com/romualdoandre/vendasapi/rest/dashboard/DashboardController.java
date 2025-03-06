package com.romualdoandre.vendasapi.rest.dashboard;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romualdoandre.vendasapi.model.repository.ClienteRepository;
import com.romualdoandre.vendasapi.model.repository.ProdutoRepository;
import com.romualdoandre.vendasapi.model.repository.VendasRepository;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	@Autowired
	private VendasRepository vendasRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	public DashboardData getDashboard() {
		long vendas = vendasRepository.count();
		long clientes = clienteRepository.count();
		long produtos = produtoRepository.count();
		var anoCorrente = LocalDate.now().getYear();
		var vendasPorMes = vendasRepository.obterSomatorioVendasPorMes(anoCorrente);
		return new DashboardData(produtos, clientes, vendas, vendasPorMes);
	}

}

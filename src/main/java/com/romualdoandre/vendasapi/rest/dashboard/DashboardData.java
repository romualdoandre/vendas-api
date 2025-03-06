package com.romualdoandre.vendasapi.rest.dashboard;

import java.util.List;

import com.romualdoandre.vendasapi.model.repository.projections.VendaPorMes;

public class DashboardData {

	private Long produtos;
	private Long clientes;
	private Long vendas;
	private List<VendaPorMes> vendasPorMes;
	
	
	public DashboardData(Long produtos, Long clientes, Long vendas, List<VendaPorMes> vendasPorMes) {
		super();
		this.produtos = produtos;
		this.clientes = clientes;
		this.vendas = vendas;
		this.vendasPorMes = vendasPorMes;
	}
	
	
	
	public DashboardData() {
		super();
	}



	public Long getProdutos() {
		return produtos;
	}
	public void setProdutos(Long produtos) {
		this.produtos = produtos;
	}
	public Long getClientes() {
		return clientes;
	}
	public void setClientes(Long clientes) {
		this.clientes = clientes;
	}
	public Long getVendas() {
		return vendas;
	}
	public void setVendas(Long vendas) {
		this.vendas = vendas;
	}



	public List<VendaPorMes> getVendasPorMes() {
		return vendasPorMes;
	}



	public void setVendasPorMes(List<VendaPorMes> vendasPorMes) {
		this.vendasPorMes = vendasPorMes;
	}
	
	
}

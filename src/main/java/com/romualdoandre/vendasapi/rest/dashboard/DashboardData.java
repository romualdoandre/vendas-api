package com.romualdoandre.vendasapi.rest.dashboard;

public class DashboardData {

	private Long produtos;
	private Long clientes;
	private Long vendas;
	
	
	
	public DashboardData(Long produtos, Long clientes, Long vendas) {
		super();
		this.produtos = produtos;
		this.clientes = clientes;
		this.vendas = vendas;
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
	
	
}

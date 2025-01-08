package com.romualdoandre.vendasapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name="produto")
public class Produto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="nome", length=100)
	private String nome;
	@Column(name="descricao", length=255)
	private String descricao;
	@Column(name="sku")
	private String sku;
	@Column(name="preco", precision=16, scale=2)
	private BigDecimal preco;
	@Column(name="data_cadastro")
	private LocalDate dataCadastro;
	
	public Produto(Long id, String nome, String descricao, String sku, BigDecimal preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.sku = sku;
		this.preco = preco;
	}
	public Produto() {
		super();
	}
	@Override
	public String toString() {
		return String.format("Produto [id=%s, nome=%s, descricao=%s, sku=%s, preco=%s]", id, nome, descricao, sku,
				preco);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	@PrePersist
	public void prePersist() {
		setDataCadastro(LocalDate.now());
	}
}

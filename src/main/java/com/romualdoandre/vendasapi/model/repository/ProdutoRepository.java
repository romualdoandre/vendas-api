package com.romualdoandre.vendasapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.romualdoandre.vendasapi.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}

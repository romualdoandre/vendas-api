package com.romualdoandre.vendasapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.romualdoandre.vendasapi.model.Venda;

public interface VendasRepository extends JpaRepository<Venda, Long>{

}

package com.romualdoandre.vendasapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.romualdoandre.vendasapi.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}

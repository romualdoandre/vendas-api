package com.romualdoandre.vendasapi.rest.vendas;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romualdoandre.vendasapi.model.repository.VendasRepository;

@RestController
@RequestMapping("/api/vendas")
@CrossOrigin("*")
public class VendasController {

	private VendasRepository repository;
}

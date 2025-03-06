package com.romualdoandre.vendasapi.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.romualdoandre.vendasapi.model.Venda;
import com.romualdoandre.vendasapi.model.repository.projections.VendaPorMes;

public interface VendasRepository extends JpaRepository<Venda, Long>{

	@Query(value="select extract(month from v.data_venda) as mes, sum(v.total) as valor\r\n"
			+ "from venda as v\r\n"
			+ "where extract(year from v.data_venda)=:ano\r\n"
			+ "group by extract(month from v.data_venda)\r\n"
			+ "order by extract(month from v.data_venda)", nativeQuery = true)
	List<VendaPorMes> obterSomatorioVendasPorMes(@Param("ano") Integer ano);
}

package com.romualdoandre.vendasapi.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

@Service
public class RelatorioVendasService {
	@Value("classpath:reports/relatorio-vendas.jrxml")
	private Resource relatorioVendas;
	@Value("classpath:reports/relatorio-vendas.jasper")
	private Resource relatorioVendasCompilado;
	@Autowired
	private DataSource dataSource;

	public byte[] gerarRelatorio() {
		
		try (Connection conn = dataSource.getConnection()){
			JasperReport report = JasperCompileManager.compileReport(relatorioVendas.getInputStream());
			Map<String, Object> params = new HashMap<>();
			JasperPrint print = JasperFillManager.fillReport(report, params, conn);
			return JasperExportManager.exportReportToPdf(print);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public byte[] gerarRelatorioCompilado() {
		
		try (Connection conn = dataSource.getConnection()){
			
			Map<String, Object> params = new HashMap<>();
			JasperPrint print = JasperFillManager.fillReport(relatorioVendasCompilado.getInputStream(), params, conn);
			return JasperExportManager.exportReportToPdf(print);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public byte[] gerarRelatorioCompiladoFacil() {
		
		try (Connection conn = dataSource.getConnection()){
			
			Map<String, Object> params = new HashMap<>();

			return JasperRunManager.runReportToPdf(relatorioVendasCompilado.getInputStream(), params, conn);
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}

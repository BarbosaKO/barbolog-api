package com.barbosa.barbolog.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.barbosa.barbolog.domain.model.Cliente;
import com.barbosa.barbolog.domain.model.Entrega;
import com.barbosa.barbolog.domain.model.StatusEntrega;
import com.barbosa.barbolog.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SolicitacaoEntregaService {
	
	private EntregaRepository entregaRepository;
	private CatalogoClienteService catalogoClienteService;
	
	@Transactional
	public Entrega solicitar(Entrega entrega) {
		
		entrega.setStatus(StatusEntrega.PENDENTE);
		entrega.setDataPedido(OffsetDateTime.now());
		Cliente cliente = catalogoClienteService.buscar(entrega.getCliente().getId());
		entrega.setCliente(cliente);
		
		return entregaRepository.save(entrega);
	}
	
	@Transactional
	public void deletar(Long entregaId) {
		entregaRepository.deleteById(entregaId);
	}
	
}

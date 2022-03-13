package com.barbosa.barbolog.domain.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbosa.barbolog.domain.model.Entrega;
import com.barbosa.barbolog.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CancelamentoEntregaService {
	
	private BuscaEntregaService buscaEntregaService;
	private EntregaRepository entregaRepository;
	
	@Transactional
	public void cancelar(Long entregaId) {
		Entrega entrega = buscaEntregaService.buscar(entregaId);
		entrega.cancelar();
		
		entregaRepository.save(entrega);
	}
}

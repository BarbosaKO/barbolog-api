package com.barbosa.barbolog.domain.service;

import org.springframework.stereotype.Service;

import com.barbosa.barbolog.domain.exception.EntidadeNaoEncontradaException;
import com.barbosa.barbolog.domain.model.Entrega;
import com.barbosa.barbolog.domain.repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuscaEntregaService {
	
	private EntregaRepository entregaRepository;
	
	public Entrega buscar(Long entregaId) {
		return entregaRepository.findById(entregaId)
		.orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada") );
	}
}

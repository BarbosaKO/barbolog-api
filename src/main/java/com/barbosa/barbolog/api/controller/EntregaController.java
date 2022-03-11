package com.barbosa.barbolog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.barbosa.barbolog.api.assembler.EntregaAssembler;
import com.barbosa.barbolog.api.model.EntregaModel;
import com.barbosa.barbolog.api.model.input.EntregaInput;
import com.barbosa.barbolog.domain.model.Entrega;
import com.barbosa.barbolog.domain.repository.EntregaRepository;
import com.barbosa.barbolog.domain.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {
	
	private EntregaRepository entregaRepository;
	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaAssembler entregaAssembler;
	
	@GetMapping
	public List<EntregaModel> listar(){
		return entregaAssembler.toCollectionModel(entregaRepository.findAll());
	} 
	
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> buscar(@PathVariable Long entregaId){
		return entregaRepository.findById(entregaId)
				.map(e -> ResponseEntity.ok( entregaAssembler.toModel(e) )
				).orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput) {
		return entregaAssembler.toModel(solicitacaoEntregaService
				.solicitar(entregaAssembler.toEntity(entregaInput))
				);
	}
	
	@PutMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> atualizar(@Valid @PathVariable Long entregaId, @RequestBody EntregaInput entregaInput){
		
		if(!entregaRepository.existsById(entregaId)) {
			return ResponseEntity.notFound().build();
		}
		
		Entrega novaEntregaUp = entregaAssembler.toEntity(entregaInput);
		novaEntregaUp.setId(entregaId);
		novaEntregaUp = solicitacaoEntregaService.solicitar(novaEntregaUp);
		return ResponseEntity.ok(entregaAssembler.toModel(novaEntregaUp));
	}
	
	@DeleteMapping("/{entregaId}")
	public ResponseEntity<Void> deletar(@PathVariable Long entregaId){
		if(!entregaRepository.existsById(entregaId)) {
			return ResponseEntity.notFound().build();
		}
		
		solicitacaoEntregaService.deletar(entregaId);
		return ResponseEntity.noContent().build();
	}
	
}

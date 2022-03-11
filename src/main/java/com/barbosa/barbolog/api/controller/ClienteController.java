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

import com.barbosa.barbolog.domain.model.Cliente;
import com.barbosa.barbolog.domain.repository.ClienteRepository;
import com.barbosa.barbolog.domain.service.CatalogoClienteService;

import lombok.AllArgsConstructor;

//Construtores via LOMBOK de forma automática
@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	//Com injenção de dependência via Anotações
	//@Autowired
	private ClienteRepository clienteRepository;
	private CatalogoClienteService catalogoClienteService;
	
	//Injeção via construtor
	/*public ClienteController(ClienteRepository clienteRepository) {
		super();
		this.clienteRepository = clienteRepository;
	}*/
	
	@GetMapping
	public List<Cliente> listar() {
		return clienteRepository.findByNomeContaining("a");
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
		
		return clienteRepository.findById(clienteId)
		.map(cliente -> ResponseEntity.ok(cliente))
		.orElse(ResponseEntity.notFound().build());
		//Poderia usar .map(ResponseEntity::ok)
		
		/*
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}else {
			return ResponseEntity.notFound().build();
		}
		*/
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return catalogoClienteService.salvar(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizar(
			@PathVariable Long clienteId, 
			@Valid @RequestBody Cliente cliente){
		
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(clienteId);
		cliente = catalogoClienteService.salvar(cliente);
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remover(@PathVariable Long clienteId){
		
		if(!clienteRepository.existsById(clienteId)) {
			return ResponseEntity.notFound().build();
		}
		
		catalogoClienteService.excluir(clienteId);
		return ResponseEntity.noContent().build();
	}
	
}

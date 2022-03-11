package com.barbosa.barbolog.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barbosa.barbolog.domain.exception.NegocioException;
import com.barbosa.barbolog.domain.model.Cliente;
import com.barbosa.barbolog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CatalogoClienteService {
	private ClienteRepository clienteRepository;
	
	public Cliente buscar(Long clienteId) {
		return clienteRepository.findById(clienteId)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado!") );
	}
	
	@Transactional
	public  Cliente salvar(Cliente cliente) {
		
		//Se achar cliente com email igual, compara os objetos. 
		//Se forem diferentes, então estão tentando cadastrar/atualizar um novo cliente com 
		//o mesmo email e isso não é permitido
		boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
				.stream()
				.anyMatch(c -> !c.equals(cliente));
		if(emailEmUso) {
			throw new NegocioException("Já existe um cliente cadastrado com o email "+cliente.getEmail());
		}
		
		return clienteRepository.save(cliente);
	}
	
	@Transactional
	public void excluir(Long idCliente) {
		clienteRepository.deleteById(idCliente);
	}
	
}

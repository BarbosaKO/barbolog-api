package com.barbosa.barbolog.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbosa.barbolog.domain.model.Cliente;

@RestController
public class ClienteController {
	
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes.add(new Cliente(1L, "Gabriel", "ga@email.com", "(11)98882-1261"));
		clientes.add(new Cliente(2L, "Gabriel Lara", "lara@email.com", "(11)98843-6969"));
		clientes.add(new Cliente(3L, "Tiago", "titi@email.com", ""));
		return clientes;
	}
}

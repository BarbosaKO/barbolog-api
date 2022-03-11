package com.barbosa.barbolog.api.assembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.barbosa.barbolog.api.model.EntregaModel;
import com.barbosa.barbolog.api.model.input.EntregaInput;
import com.barbosa.barbolog.domain.model.Entrega;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EntregaAssembler {
	
	private ModelMapper modelMapper;
	
	public EntregaModel toModel(Entrega entrega) {
		return modelMapper.map(entrega, EntregaModel.class);
	}
	
	public List<EntregaModel> toCollectionModel(List<Entrega> entregas){
		
		List<EntregaModel> entregasModels = new ArrayList<EntregaModel>();
		entregas.forEach(e -> entregasModels.add( modelMapper.map(e, EntregaModel.class) ));
		
		return entregasModels;
	}
	
	public Entrega toEntity(EntregaInput entregaInput) {
		return modelMapper.map(entregaInput, Entrega.class);
	}
	
}

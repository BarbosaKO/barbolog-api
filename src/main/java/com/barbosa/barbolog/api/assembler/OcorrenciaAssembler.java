package com.barbosa.barbolog.api.assembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.barbosa.barbolog.api.model.OcorrenciaModel;
import com.barbosa.barbolog.domain.model.Entrega;
import com.barbosa.barbolog.domain.model.Ocorrencia;
import com.barbosa.barbolog.domain.service.BuscaEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class OcorrenciaAssembler {
	
	private ModelMapper modelMapper;
	private BuscaEntregaService buscaEntregaService;
	
	public OcorrenciaModel toModel(Ocorrencia ocorrencia) {
		return modelMapper.map(ocorrencia, OcorrenciaModel.class);
	}
	
	public Ocorrencia toEntity(OcorrenciaModel ocorrenciaModel) {
		return modelMapper.map(ocorrenciaModel, Ocorrencia.class);
	}
	
	public List<OcorrenciaModel> toCollectionModel(Long entregaId){
		
		Entrega entrega = buscaEntregaService.buscar(entregaId);
		List<OcorrenciaModel> ocorrenciasModels = new ArrayList<OcorrenciaModel>();
		
		entrega.getOcorrencias()
		.forEach(o -> ocorrenciasModels.add( this.toModel(o) ) );
		
		return ocorrenciasModels;
	}
	
}

package com.barbosa.barbolog.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.barbosa.barbolog.domain.exception.EntidadeNaoEncontradaException;
import com.barbosa.barbolog.domain.exception.NegocioException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Problema.Campo> campos = new ArrayList<Problema.Campo>();
		
		ex.getBindingResult().getAllErrors()
			.forEach(
				err -> campos.add(
						new Problema.Campo( 
									((FieldError) err).getField(), 
									messageSource.getMessage(err, LocaleContextHolder.getLocale())
								)
						)
			);
		
		Problema problema = new Problema(
				status.value(), 
				OffsetDateTime.now(), 
				"Um ou mais campos estão inválidos! Faça o preenchimento corretamente.",
				campos
		);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problema problema = new Problema(
				status.value(), 
				OffsetDateTime.now(), 
				ex.getMessage(),
				null
		);
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problema problema = new Problema(
				status.value(), 
				OffsetDateTime.now(), 
				ex.getMessage(),
				null
		);
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}
	
}

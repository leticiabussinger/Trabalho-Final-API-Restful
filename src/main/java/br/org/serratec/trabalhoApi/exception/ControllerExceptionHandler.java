package br.org.serratec.trabalhoApi.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> erros = new ArrayList<>();

		for (FieldError e : ex.getBindingResult().getFieldErrors()) {
			erros.add(e.getField() + ": " + e.getDefaultMessage());
		}

		ErroResposta erroResposta = new ErroResposta(status.value(),
				"Existem Campos Inválidos. Confira o preenchimento", LocalDateTime.now(), erros);

		return super.handleExceptionInternal(ex, erroResposta, headers, status, request);
	}

	@ExceptionHandler({ EmailException.class, SenhaException.class, FotoException.class, NullException.class })
	protected ResponseEntity<?> handleEmailException(Exception ex) {
		return ResponseEntity.unprocessableEntity().body(ex.getMessage());
	}
	
	@ExceptionHandler(RecursoNaoEncontradoException.class)
    protected ResponseEntity<?> handleResourceNotFoundException(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
	    String message = "Ocorreu um erro de integridade de dados.";

	    if (ex.getCause() instanceof ConstraintViolationException) {
	        ConstraintViolationException cause = (ConstraintViolationException) ex.getCause();

	        if (cause.getSQLException() != null && cause.getSQLException().getSQLState().equals("23505")) {
	            message = "Já existe um registro com esse valor.";
	        } else if (cause.getSQLException() != null && cause.getSQLException().getSQLState().equals("23503")) {
	            message = "Chave estrangeira não encontrada.";
	        }
	    }

	    HttpStatus status = HttpStatus.BAD_REQUEST;
	    return handleExceptionInternal(ex, message, new HttpHeaders(), status, request);
	}
}

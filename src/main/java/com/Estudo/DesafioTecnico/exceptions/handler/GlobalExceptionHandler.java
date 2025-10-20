package com.Estudo.DesafioTecnico.exceptions.handler;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.Estudo.DesafioTecnico.exceptions.ResourceNotFoundException;

@ControllerAdvice // Aplica o handler a todos os controllers (@RestController)
	public class GlobalExceptionHandler {

	    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	    /**
	     * TRATAMENTO PARA EXCEÇÕES DE RECURSO NÃO ENCONTRADO (404 Not Found)
	     */
	    @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
	            ResourceNotFoundException ex, WebRequest request) {

	        HttpStatus status = HttpStatus.NOT_FOUND;
	        
	        logger.warn("Resource Not Found: {} - {}", ex.getMessage(), request.getDescription(false));

	        ErrorResponse errorResponse = new ErrorResponse(
	                status.value(),
	                status.getReasonPhrase(), // "Not Found"
	                ex.getMessage(),
	                request.getDescription(false).replace("uri=", "")
	        );
	        return new ResponseEntity<>(errorResponse, status);
	    }

	   
	   
	    
	    /**
	     * TRATAMENTO GENÉRICO (Fallback para todas as outras exceções, 500 Internal Server Error)
	     * BOAS PRÁTICAS: Logar o Stack Trace mas retornar uma mensagem genérica para o cliente.
	     */
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleGenericException(
	            Exception ex, WebRequest request) {

	        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	        
	        // Loga o erro COMPLETO para o time de desenvolvimento
	        logger.error("Internal Server Error: {}", ex.getMessage(), ex);

	        ErrorResponse errorResponse = new ErrorResponse(
	                status.value(),
	                status.getReasonPhrase(), // "Internal Server Error"
	                // EVITE EXPOR DETALHES INTERNOS!
	                "An unexpected error occurred. Please contact support.", 
	                request.getDescription(false).replace("uri=", "")
	        );
	        return new ResponseEntity<>(errorResponse, status);
	    }
	}


package ${packageName}; 

import java.sql.SQLException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.anteros.core.utils.ExceptionUtils;
import br.com.anteros.persistence.session.repository.SQLRepositoryException;
import br.com.anteros.validation.api.ConstraintViolation;
import br.com.anteros.validation.api.ConstraintViolationException;

@ControllerAdvice
public class MvcExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleIllegalArgumentException(Exception ex) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);

		if (ex instanceof SQLRepositoryException && ex.getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException exc = (ConstraintViolationException) ex.getCause();
			String msg = "";
			boolean appendDelimiter = false;
			for (ConstraintViolation cv : exc.getConstraintViolations()) {
				if (appendDelimiter)
					msg = msg + "\n";
				msg = msg + cv.getMessage();
				appendDelimiter = true;
			}
			return new ResponseEntity<String>(msg, headers, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			Throwable fromCause = ExceptionUtils.extractExceptionFromCause(ex, SQLException.class);
			if (fromCause != null) {
				return new ResponseEntity<String>(ex.getMessage() + " Causa: " + fromCause.getMessage()
						+ " Tipo de exceção: " + ex.getClass().getSimpleName(), headers,
						HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				return new ResponseEntity<String>(ex.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}

}

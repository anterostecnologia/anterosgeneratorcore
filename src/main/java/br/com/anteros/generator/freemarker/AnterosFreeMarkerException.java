package br.com.anteros.generator.freemarker;

public class AnterosFreeMarkerException extends RuntimeException {

	public AnterosFreeMarkerException() {
	}

	public AnterosFreeMarkerException(String message) {
		super(message);
	}

	public AnterosFreeMarkerException(Throwable cause) {
		super(cause);
	}

	public AnterosFreeMarkerException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnterosFreeMarkerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

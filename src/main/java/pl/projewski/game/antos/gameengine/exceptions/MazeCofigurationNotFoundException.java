package pl.projewski.game.antos.gameengine.exceptions;

public class MazeCofigurationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3051492408110452699L;

	public MazeCofigurationNotFoundException() {
		super();
	}

	public MazeCofigurationNotFoundException(String message, Throwable cause, boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MazeCofigurationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MazeCofigurationNotFoundException(String message) {
		super(message);
	}

	public MazeCofigurationNotFoundException(Throwable cause) {
		super(cause);
	}

}

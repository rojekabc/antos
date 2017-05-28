package pl.projewski.game.antos.gameengine.exceptions;

public class CreatureAmountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6427938146143572230L;

	public CreatureAmountNotFoundException() {
		super();
	}

	public CreatureAmountNotFoundException(String message, Throwable cause, boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CreatureAmountNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CreatureAmountNotFoundException(String message) {
		super(message);
	}

	public CreatureAmountNotFoundException(Throwable cause) {
		super(cause);
	}

}

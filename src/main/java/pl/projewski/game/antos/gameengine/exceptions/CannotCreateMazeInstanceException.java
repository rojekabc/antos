/**
 * 
 */
package pl.projewski.game.antos.gameengine.exceptions;

/**
 * @author piotrek
 *
 */
public class CannotCreateMazeInstanceException extends RuntimeException {

	private static final long serialVersionUID = -1634563522437192899L;

	/**
	 * 
	 */
	public CannotCreateMazeInstanceException() {
	}

	/**
	 * @param message
	 */
	public CannotCreateMazeInstanceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CannotCreateMazeInstanceException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CannotCreateMazeInstanceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CannotCreateMazeInstanceException(String message, Throwable cause, boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

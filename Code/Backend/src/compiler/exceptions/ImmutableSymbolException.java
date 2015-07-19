package compiler.exceptions;

public class ImmutableSymbolException extends Exception {

	private static final long serialVersionUID = 1L;

	public ImmutableSymbolException() {
		super("Trying to change the value of a immutable symbol.");
	}

	/**
	 * This exception is thrown whenever the user code is trying to change the value of 
	 * immutable symbol. 
	 * 
	 * @param symbolName - Name of the immutable symbol
	 */
	public ImmutableSymbolException(String symbolName) {
		super("Trying to change the value of an immutable symbol : '" + symbolName + "'");
	}
}

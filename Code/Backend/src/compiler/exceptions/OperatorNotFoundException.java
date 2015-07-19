package compiler.exceptions;

public class OperatorNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OperatorNotFoundException() {
		super("Usage of undefined operator.");
	}

	/**
	 * This exception is thrown whenever there is a type mismatch between two symbols.
	 * 
	 * @param found - Data-type being passed.
	 * @param required - Actual data-type that is expected
	 */
	public OperatorNotFoundException(String operatorName) {
		super("Unknown operator : '" + operatorName + "'");
	}

}

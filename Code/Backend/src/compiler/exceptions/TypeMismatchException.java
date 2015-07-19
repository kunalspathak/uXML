package compiler.exceptions;

import compiler.util.Constants;

public class TypeMismatchException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public TypeMismatchException() {
		super("Type mismatch.");
	}

	/**
	 * This exception is thrown whenever there is a type mismatch between two symbols.
	 * 
	 * @param found - Data-type being passed.
	 * @param required - Actual data-type that is expected
	 */
	public TypeMismatchException(String found, String required) {
		super(Constants.NO_TYPE.equals(found)? "Usage of uninitialized symbol." : 
			"Type mismatch \n Found    : '" + found + "' \n Required : '" + required + "'");
	}

}

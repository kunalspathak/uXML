/**
 * 
 */
package compiler.exceptions;

import compiler.dataTypes.FunctionType;

/**
 * @author kunal
 *
 */
public class SignatureMismatchException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SignatureMismatchException() {
		super("Type mismatch.");
	}

	/**
	 * This exception is thrown whenever there is a type mismatch between two symbols.
	 * 
	 * @param found - Data-type being passed.
	 * @param required - Actual data-type that is expected
	 */
	public SignatureMismatchException(FunctionType function, String args) {
		super("Signature mismatch \n Found    : " + args + " \n Required : " + function);
	}

}

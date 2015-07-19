package compiler.exceptions;

public class IllegalSymbolValueException extends Exception {
	
	private static final long serialVersionUID = 1L;
	

	/**
	 * This exception is thrown whenever there is a type mismatch in type of symbol value that is 
	 * getting assigned and the actual symbol. 
	 * 
	 * @param symbolType - Symbol type
	 */
	public IllegalSymbolValueException(String symbolType) {
		super("Illegal data-type \n Required type  : " + symbolType);
	}

	/**
	 * This exception is thrown whenever there is a type mismatch in type of symbol value that is 
	 * getting assigned and the actual symbol. 
	 * 
	 * @param required - Symbol type
	 * @param received - Value of a symbol that is sent for assignment
	 */
	public IllegalSymbolValueException(String required, String received) {
		super("Illegal data-type \n Required type  : '" + required + "'\n Received value : '" + received + "'");
	}
}

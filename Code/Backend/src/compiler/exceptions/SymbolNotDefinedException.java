package compiler.exceptions;

/**
 * Exception class that is used if any symbols are not present in symbol table, 
 * but still tried to retrieve it.  
 * @author kunal
 *
 */
public class SymbolNotDefinedException extends Exception {

	private static final long serialVersionUID = 1L;

	public SymbolNotDefinedException() {
		super("Undefined symbol.");
	}

	public SymbolNotDefinedException(String symbolName) {
		super("Undefined symbol : " + symbolName);
	}
}

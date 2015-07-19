package parserOfJ.exceptions;

/**
 * Front-end compilation errors
 * @author kunal
 *
 */
public class CompilationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CompilationException(String message) {
		super("Compilation Error : " + message);
	}
	
	public CompilationException(String msgType, String message) {
		super("Compilation Error : " + msgType + "\n" + message);
	}

}

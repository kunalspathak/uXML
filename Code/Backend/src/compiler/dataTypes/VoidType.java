package compiler.dataTypes;

import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;


/**
 * Void
 * @author kunal
 *
 */
public class VoidType extends Symbol {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected VoidType(String name, boolean mutable) {
		super(name, Constants.VOID, mutable);
	}

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public void setValue(Symbol symbol) throws IllegalSymbolValueException,
			TypeMismatchException, ImmutableSymbolException {

	}

	@Override
	public String toString() {
		return null;
	}

}

package compiler.dataTypes;

import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;


public class CustomType extends Symbol {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String value = null;
	
	CustomType(String name,String type, boolean mutable) {
		super(name,type,mutable);
	}

	@Override
	public String toString() {
		return /*getName() + " : " + */getType();
	}

	@Override
	public String getValue() {
		return value;
	}
	
	/**
	 * Parse the given value and return the string value.
	 * @param value
	 * @return
	 * @throws SymbolNotDefinedException
	 * @throws IllegalSymbolValueException
	 */
	public static String parseValue(String value) {
		/*// Whenever constants refer to a variable 
		if(Environment.globalSymTab.containsSymbol(value))
			return ((StringType)Environment.globalSymTab.getSymbol(value)).value();
		else		*/
		return  new String(value);
	}
	
	@Override
	public void setValue(Symbol symbol) throws IllegalSymbolValueException, TypeMismatchException, ImmutableSymbolException {
		
		// If current symbol is not mutable, then thrown the exception
		if(!isMutable())
			throw new ImmutableSymbolException(name);	

		if(this.getDataType() == symbol.getDataType())
			this.value = symbol.getValue();
		else
			throw new TypeMismatchException(symbol.getType(), this.getType());
	}
	
	public void setValue(String value) throws ImmutableSymbolException {
		
		// If current symbol is not mutable, then thrown the exception
		if(!isMutable())
			throw new ImmutableSymbolException(name);	

		this.value = value;
	}
	/*@Override
	public String value() {
		return getValue();
	}*/
	
}

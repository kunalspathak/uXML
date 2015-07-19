package compiler.dataTypes;

import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;


/**
 * Represent Symbol of type CharType
 * @author kunal
 *
 */
public class CharType extends Symbol {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * CharType Symbol Value
	 */
	private char value;
	
	
	/**
	 * Constructor that takes the name and value of CharType symbol.
	 * Mostly used while declaring CharType variables.
	 * @param name
	 * @param value
	 * @throws IllegalSymbolValueException
	 * @throws SymbolNotDefinedException 
	 */
	CharType(String name, String value, boolean mutable) throws IllegalSymbolValueException {
		super(name,Constants.CHAR,mutable);
		if(value != null)
			this.value = parseValue(value);
		
	}

	@Override
	public String toString() {
		return /*getName() + " : " + */String.valueOf(this.value); 
	}


	public Character value() {
		return this.value;
	}
	
	/**
	 * Parse the given value and return the char value.
	 * @param value
	 * @return
	 * @throws SymbolNotDefinedException
	 * @throws IllegalSymbolValueException
	 */
	public static char parseValue(String value) throws IllegalSymbolValueException {
		if(value.length() != 1)
			throw new IllegalSymbolValueException(Constants.CHAR,value);
		
		/*// Whenever constants refer to a variable 
		if(Environment.globalSymTab.containsSymbol(value))
			return ((CharType)Environment.globalSymTab.getSymbol(value)).value();
		else*/
			return value.charAt(0);
	}
	
	@Override
	public void setValue(Symbol symbol) throws IllegalSymbolValueException, TypeMismatchException, ImmutableSymbolException {
		
		// If current symbol is not mutable, then thrown the exception
		if(!isMutable())
			throw new ImmutableSymbolException(name);	

		if(this.getDataType() == symbol.getDataType())
			this.value = parseValue(symbol.getValue());
		else
			throw new TypeMismatchException(symbol.getType(), this.getType());
	}
	
	/**
	 * Sets the value to the primitive char value that is passed as an argument
	 * @param value
	 * @throws ImmutableSymbolException 
	 */
	public void setValue(char value) throws ImmutableSymbolException {
		
		// If current symbol is not mutable, then thrown the exception
		if(!isMutable())
			throw new ImmutableSymbolException(name);	

		this.value = value;
	}

	@Override
	public String getValue()  {
		return String.valueOf(value);
	}
}

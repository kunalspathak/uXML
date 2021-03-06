package compiler.dataTypes;

import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;


/**
 * Represent Symbol of type Integer
 * @author kunal
 * 
 */
public class IntegerType extends Symbol{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * IntegerType Symbol Value
	 */
	private int value;
	
	
	/**
	 * Constructor that takes the name and value of IntegerType symbol.
	 * Mostly used while declaring IntegerType variables.
	 * @param name
	 * @param value
	 * @throws IllegalSymbolValueException
	 * @throws SymbolNotDefinedException 
	 */
	IntegerType(String name, String value, boolean mutable) throws IllegalSymbolValueException  {
		super(name,Constants.INT,mutable);
		
		if(value != null)
			this.value = parseValue(value);
	}


	@Override
	public String toString() {
		return /*getName() + " : " + */String.valueOf(this.value); 
	}

	public int value() {
		return this.value;
	}
	
	/**
	 * Parse the given value and return the int value.
	 * @param value
	 * @return
	 * @throws SymbolNotDefinedException
	 * @throws IllegalSymbolValueException
	 */
	public static int parseValue(String value) throws IllegalSymbolValueException {
		try {
			return Integer.parseInt(value);
		} catch(NumberFormatException ne) {
			/*// Whenever constants refer to a variable 
			if(Environment.globalSymTab.containsSymbol(value))
				return ((IntegerType)Environment.globalSymTab.getSymbol(value)).value();
			else*/
				throw new IllegalSymbolValueException(Constants.INT,value);
		}
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
	 * Sets the value to the primitive int value that is passed as an argument
	 * @param value
	 */
	public void setValue(int value) throws ImmutableSymbolException {
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

package compiler.dataTypes;

import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;


/**
 * Represent Symbol of type BooleanType
 * @author kunal
 *
 */
public class BooleanType extends Symbol{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * BooleanType Symbol Value
	 */
	private boolean value;

	
	/**
	 * Constructor that takes the name and value of BooleanType symbol.
	 * Mostly used while declaring BooleanType variables.
	 * @param name
	 * @param value
	 * @throws SymbolNotDefinedException 
	 * @throws IllegalSymbolValueException
	 */
	BooleanType(String name, String value,boolean mutable) {
		super(name,Constants.BOOLEAN, mutable);
		if(value != null)
			this.value = parseValue(value);
		
	}


	@Override
	public String toString() {
		return /*getName() + " : " + */String.valueOf(this.value); 
	}


	public Boolean value(){
		return this.value;
	}
	
	/**
	 * Parse the given value and return the boolean value.
	 * @param value
	 * @return
	 * @throws SymbolNotDefinedException
	 * @throws IllegalSymbolValueException
	 */
	public static boolean parseValue(String value) {
		/*// Whenever constants refer to a variable 
		if(Environment.globalSymTab.containsSymbol(value))
			return ((BooleanType)Environment.globalSymTab.getSymbol(value)).value();
		else*/
			return Boolean.parseBoolean(value);
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
	 * Sets the value to the primitive boolean value that is passed as an argument
	 * @param value
	 * @throws ImmutableSymbolException 
	 */
	public void setValue(boolean value) throws ImmutableSymbolException {
		
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

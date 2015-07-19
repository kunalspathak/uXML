package compiler.dataTypes;

import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;


/**
 * Represent Symbol of type StringType
 * @author kunal
 *
 */
public class StringType extends Symbol{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * StringType Symbol Value
	 */
	private String value;
	
	
	
	/**
	 * Constructor that takes the name and value of StringType symbol.
	 * Mostly used while declaring StringType variables.
	 * @param name
	 * @param value
	 * @throws SymbolNotDefinedException 
	 * @throws IllegalSymbolValueException
	 */
	StringType(String name, String value, boolean mutable)  {
		super(name,Constants.STRING,mutable);
		if(value != null)
			this.value = parseValue(value);
	}


	@Override
	public String toString() {
		return /*getName() + " : " + */this.value; 
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
			this.value = parseValue(symbol.getValue());
		else
			throw new TypeMismatchException(symbol.getType(), this.getType());
	}
	
	public void setValue(String value) throws ImmutableSymbolException {
		
		// If current symbol is not mutable, then thrown the exception
		if(!isMutable())
			throw new ImmutableSymbolException(name);	

		this.value = value;
	}
	
	@Override
	public String getValue()  {
		return value();
	}
	
	public String value() {
		return this.value;
	}

}

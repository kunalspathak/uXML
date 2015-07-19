package compiler.dataTypes;

import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;


/**
 * Represent Symbol of type DecimalType
 * @author kunal
 *
 */
public class DecimalType extends Symbol {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * DecimalType Symbol Value
	 */
	private float value;
	
	
	/**
	 * Constructor that takes the name and value of DecimalType symbol.
	 * Mostly used whil declaring DecimalType variables.
	 * @param name
	 * @param value
	 * @throws IllegalSymbolValueException
	 * @throws SymbolNotDefinedException 
	 */
	DecimalType(String name, String value, boolean mutable) throws IllegalSymbolValueException {
		super(name,Constants.FLOAT,mutable);
		
		if(value != null)
			this.value = parseValue(value);
	}


	@Override
	public String toString() {
		return /*getName() + " : " + */String.valueOf(this.value); 
	}


	public float value() {
		return this.value;
	}

	/**
	 * Parse the given value and return the decimal value.
	 * @param value
	 * @return
	 * @throws SymbolNotDefinedException
	 * @throws IllegalSymbolValueException
	 */
	public static float parseValue(String value) throws IllegalSymbolValueException {
		try {
			return Float.parseFloat(value);
		} catch(NumberFormatException ne) {
			/*// Whenever constants refer to a variable 
			if(Environment.globalSymTab.containsSymbol(value))
				return ((DecimalType)Environment.globalSymTab.getSymbol(value)).value();
			else*/
				throw new IllegalSymbolValueException(Constants.FLOAT,value);
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
	 * Sets the value to the primitive float value that is passed as an argument
	 * @param value
	 * @throws ImmutableSymbolException 
	 */
	public void setValue(float value) throws ImmutableSymbolException {
		
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

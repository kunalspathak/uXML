package compiler.components;

import compiler.dataTypes.BooleanType;
import compiler.dataTypes.CharType;
import compiler.dataTypes.ConstantValue;
import compiler.dataTypes.DecimalType;
import compiler.dataTypes.IntegerType;
import compiler.dataTypes.StringType;
import compiler.exceptions.IllegalSymbolValueException;



/**
 * 
 * This interface provides various implementation of operators (arithmetic, logical and conditional)
 * that are used in programming languages.
 * @author kunal
 *
 *TODO Move all the methods to util
 */
public abstract class Operators {
	
	/**
	 * This method is used when an operator requires just 1 argument.
	 * It returns the result of the operation, however if the operation results in 
	 * violation of data-type of a result, the method returns null.
	 * @param rhs
	 */
	public abstract Symbol evaluate(Symbol rhs) throws IllegalSymbolValueException;
	
	/**
	 * This method is used when an operator requires 2 operators, lhs and rhs.
	 * @param lhs
	 * @param rhs
	 */
	public abstract Symbol evaluate(Symbol lhs, Symbol rhs) throws IllegalSymbolValueException;
	
	/**
	 * This method returns the integer value associated to a variable/constant
	 * @param symbol - Symbol 
	 * @return - int value
	 */
	public int intValue(Symbol symbol) {
		
		int value;
		if(symbol instanceof ConstantValue) 
			value = ((ConstantValue)symbol).intValue();
		else
			value = ((IntegerType)symbol).value();
		
		return value;
	}
	
	/**
	 * This method returns the decimal value associated to a variable/constant
	 * @param symbol - Symbol 
	 * @return - decimal value
	 */
	public float floatValue(Symbol symbol) {
		
		float value;
		if(symbol instanceof ConstantValue) 
			value = ((ConstantValue)symbol).decimalValue();
		else
			value = ((DecimalType)symbol).value();
		
		return value;
	}
	
	/**
	 * This method returns the String value associated to a variable/constant
	 * @param symbol - Symbol 
	 * @return - String value
	 */
	public String stringValue(Symbol symbol) {
		
		String value;
		if(symbol instanceof ConstantValue) 
			value = ((ConstantValue)symbol).stringValue();
		else
			value = ((StringType)symbol).value();
		
		return value;
	}
	
	/**
	 * This method returns the String value associated to a variable/constant
	 * @param symbol - Symbol 
	 * @return - String value
	 */
	public boolean booleanValue(Symbol symbol) {
		
		boolean value;
		if(symbol instanceof ConstantValue) 
			value = ((ConstantValue)symbol).booleanValue();
		else
			value = ((BooleanType)symbol).value();
		
		return value;
	}
	
	/**
	 * This method returns the char value associated to a variable/constant
	 * @param symbol - Symbol 
	 * @return - String value
	 */
	public char charValue(Symbol symbol) {
		
		char value;
		if(symbol instanceof ConstantValue) 
			value = ((ConstantValue)symbol).charValue();
		else
			value = ((CharType)symbol).value();
		
		return value;
	}

}

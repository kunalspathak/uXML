package compiler.components;

import java.io.Serializable;

import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;
import compiler.util.Constants.DATA_TYPE;



/**
 * This abstract class represents a symbol in a symbol table. 
 * It can store information having standard data-type like integer, decimal,
 * string, boolean or user defined data-type like class-type, pointer-type,
 * enum-type, array-type. The list can be expanded by making the data-type
 * class extend this class.
 * 
 * @author kunal
 *
 */

public abstract class Symbol implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name = null;
	protected String type = null;
	protected DATA_TYPE datatype = null;
	private boolean mutable = false;
	

	/**
	 * Sets the Symbol name and type. 
	 * Used by standard symbol-types.
	 * @param name - Symbol name
	 * @param type - Symbol type
	 */
	protected Symbol(String name, String type, boolean mutable) {
		this.name = name;
		this.type = type;
		this.mutable = mutable;
		
		if(Constants.INT.equals(type)) 
			datatype = DATA_TYPE.INT;
		else if(Constants.FLOAT.equals(type))
			datatype = DATA_TYPE.FLOAT;
		else if(Constants.STRING.equals(type)) 
			datatype = DATA_TYPE.STRING;
		else if(Constants.BOOLEAN.equals(type)) 
			datatype = DATA_TYPE.BOOL;
		else if(Constants.CHAR.equals(type))
			datatype = DATA_TYPE.CHAR;
		else if(Constants.NO_TYPE.equals(type))
			datatype = DATA_TYPE.DYNAMIC;
		else if(Constants.FUNCTION.equals(type))
			datatype = DATA_TYPE.FUNCTION;
		else if(Constants.OVERLOADED_FUNCTIONS.equals(type))
			datatype = DATA_TYPE.OVERLOADED_FUNCTIONS;
		else if(Constants.POINTER.equals(type))
			datatype = DATA_TYPE.POINTER;
		else if(Constants.ARRAY.equals(type))
			datatype = DATA_TYPE.ARRAY;
		else if(Constants.CLASS.equals(type))
			datatype = DATA_TYPE.CLASS;
		else 
			datatype = DATA_TYPE.USERDEFINED;
	}
	
	/**
	 * The toString() method can be used to print the contents of current Symbol
	 * Eg. If Symbol represents IntegerType, then this method could be used to 
	 * print the value of that integer and like-wise. For user defined data-types, 
	 * this method will print the address of the location where the current Symbol is stored.
	 * @return - String value of SymbolValue
	 */
	@Override
	public abstract String toString();
	
	
	/**
	 * This method returns the name of current Symbol.   
	 * @return - Symbol name
	 */
	public String getName(){
		return this.name;
	}
	
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the type of current Symbol.
	 * @return - Symbol type
	 */
	public String getType(){
		return this.type;
	}
	
	
	/**
	 * 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * This method returns the data_type of current Symbol.
	 * @return
	 */
	public DATA_TYPE getDataType() {
		return this.datatype;
	}
	
	/**
	 * Sets the value of current Symbol to that of 'symbol'
	 * @param value
	 * @throws IllegalSymbolValueException
	 */
	public abstract void setValue(Symbol symbol) throws IllegalSymbolValueException, TypeMismatchException, ImmutableSymbolException ;
	
	/**
	 * Gets the value of current Symbol in String format
	 * @param value
	 * @throws IllegalSymbolValueException
	 */
	public abstract String getValue()  ;
	
	/**
	 * This method makes the current symbol as mutable. It is used when it is known that the 
	 * current symbol is immutable, but still there is a requirement to make it mutable, change
	 * the value and then again make it immutable.
	 *  
	 */
	public void makeMutable() {
		this.mutable = true;
	}
	
	
	/**
	 * As mentioned in the javadocs of above method, there might be a need to make a symbol mutable and
	 * then revert back the change.
	 */
	public void makeImmutable() {
		this.mutable = false;
	}
	
	/**
	 * Sets the mutable status of the symbol
	 * @param mutable
	 */
	public void setMutableStatus(boolean mutable) {
		this.mutable = mutable;
	}

	/**
	 * If this is a pointee, and if it is false, then return false.
	 * However if this is not a pointee then return the value of mutable
	 * @return the mutable
	 */
	public boolean isMutable() {
		return mutable;
	}

}

package compiler.dataTypes;

import java.util.ArrayList;

import compiler.components.Environment;
import compiler.components.Symbol;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.util.Constants;



/**
 * This class contains methods that takes symbol and find the base data-type of a symbol, in case
 * it is inherited from custom defined symbols.
 * @author kunal
 *
 */
public class DataTypeFinder {
	
	public static String getDataType(IntegerType intValue) {
		return Constants.INT;
	}
	
	public static String getDataType(DecimalType decimalValue) {
		return Constants.FLOAT;
	}
	
	public static String getDataType(StringType stringValue) {
		return Constants.STRING;
	}
	
	public static String getDataType(CharType charValue) {
		return Constants.CHAR;
	}
	
	public static String getDataType(BooleanType booleanValue) {
		return Constants.BOOLEAN;
	}
	
	public static String getDataType(CustomType customValue) {
		return Constants.NO_TYPE;
	}
	
	/**
	 * Returns the base data-type of the 
	 * @param function
	 * @return
	 * @throws SymbolNotDefinedException 
	 */
	public static String getDataType(FunctionType function) throws SymbolNotDefinedException {
		StringBuffer functionType = new StringBuffer("(");
		ArrayList<Symbol> formalArgs = function.getFormalParameters();
		if(formalArgs.size() > 0)
			functionType.append(getDataType(formalArgs.get(0)));
		
		for(int argNo = 1; argNo < formalArgs.size(); argNo++)
			functionType.append(",")
				.append(getDataType(formalArgs.get(argNo)));
		functionType.append(")");
		return functionType.toString();
	}
	
	/**
	 * Returns the base date-type of symbol to which this pointer points to.
	 * @param pointer
	 * @return
	 * @throws SymbolNotDefinedException 
	 */
	public static String getDataType(PointerType pointer) throws SymbolNotDefinedException {
		return getDataType(pointer.getReferenceType()) + "*";
	}
	
	/**
	 * Returns the base data-type of symbol that are present in this array.
	 * @param array
	 * @return
	 * @throws SymbolNotDefinedException 
	 */
	public static String getDataType(ArrayType array) throws SymbolNotDefinedException {
		StringBuilder arrayString = new StringBuilder("");
		int dimSize = array.getDimensionSize();
		String arrayElementType = getDataType(array.getArrayElementType());
		
		for(int dim = 0; dim < dimSize;dim++)
			arrayString.append("[")
				.append(arrayElementType)
				.append("]");
		return arrayString.toString();
	}
	
	/**
	 * Returns the type of class and object name
	 * @param classObj
	 * @return
	 */
	public static String getDataType(ClassType classObj) {
		return classObj.getClassName();
	}
	
	public static String getDataType(Symbol symbol) throws SymbolNotDefinedException {
		/*if(Constants.INT.equals(symbol.getType())) 
			return getDataType((IntegerType)symbol);
		else if (Constants.FLOAT.equals(symbol.getType()))
			return getDataType((DecimalType)symbol);
		else if (Constants.STRING.equals(symbol.getType()))
			return getDataType((StringType)symbol);
		else if (Constants.CHAR.equals(symbol.getType()))
			return getDataType((CharType)symbol);
		else if (Constants.BOOLEAN.equals(symbol.getType()))
			return getDataType((BooleanType)symbol);*/
		String symbolType = symbol.getType();
		if(Constants.INT.equals(symbolType) || Constants.FLOAT.equals(symbolType)  || Constants.STRING.equals(symbolType)
				|| Constants.CHAR.equals(symbolType) || Constants.BOOLEAN.equals(symbolType) || Constants.NO_TYPE.equals(symbolType))
			return symbolType;
		else if (Constants.FUNCTION.equals(symbolType))
			return getDataType((FunctionType)symbol);
		else if (Constants.POINTER.equals(symbolType))
			return getDataType((PointerType)symbol);
		else if (Constants.ARRAY.equals(symbolType))
			return getDataType((ArrayType)symbol);
		else if(Constants.CLASS.equals(symbolType))
			return getDataType((ClassType)symbol);
		else {
			String customSymbolType = symbol.getType();
			if(Constants.OVERLOADED_FUNCTIONS.equals(customSymbolType)) {
				//TODO: Currently the first entry of overloaded function is extracted
				// Need to fix it to extract the function depending on parameter types
				return getDataType(((OverloadedFunctionType)Environment.getSymbol(symbol.getName())).getDefaultFunction()); 
			}
			else
				return getDataType(Environment.getSymbol(customSymbolType));
		}
	}
	
	/**
	 * This method is used to get the data-type that is being used in :
	 * referenceType - Pointers 
	 * symbolType of 1st element - Array
	 * function parameters - Function
	 * 
	 * @param symbolType
	 * @return
	 * @throws SymbolNotDefinedException
	 */
	public static String getDataType(String symbolType) throws SymbolNotDefinedException {
		if(Constants.INT.equals(symbolType) || Constants.FLOAT.equals(symbolType)  || Constants.STRING.equals(symbolType)
				|| Constants.CHAR.equals(symbolType) || Constants.BOOLEAN.equals(symbolType))
			return symbolType;
			
		// Since the symbolType will be either basic type or name of defined complex-type (functions, pointers, array)
		else
			return getDataType(Environment.getSymbol(symbolType));
	}
	
	public static Symbol getOverloadedFunction(Symbol signature, OverloadedFunctionType functionList) {
		return null;
		
	}
	
	/**
	 * Checks if given data-type is one of the system defined i.e. 
	 * int, decimal, string, boolean, char, function, pointer, array 
	 * 
	 * @param dataType
	 * @return
	 *//*
	private boolean isSystemDefined(DATA_TYPE dataType) {
		switch(dataType) {
		case INT:
		case FLOAT:
		case STRING:
		case CHAR:
		case BOOL:
		case FUNCTION:
		case POINTER:
		case ARRAY:
			return true;
		default:
			return false;
		}
	}*/
	
	

}

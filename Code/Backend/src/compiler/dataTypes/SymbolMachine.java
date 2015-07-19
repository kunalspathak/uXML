package compiler.dataTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.w3c.dom.Node;

import compiler.components.Environment;
import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Clone;
import compiler.util.Constants;


/**
 * This class deals with various utilities of Symbol like generate, cast and get individual
 * values of primitive data-type
 * @author kunal
 *
 */
public class SymbolMachine {
	
	/**
	 * This method generates the symbol of name, type and mutable status that it receives as parameters
	 * @param name - Name of the symbol to be generated
	 * @param type - Datatype of the symbol to be generated
	 * @param mutable - Mutable status of the symbol to be generated
	 * @return
	 * @throws IllegalSymbolValueException
	 * @throws ImmutableSymbolException
	 * @throws TypeMismatchException
	 */
	public static Symbol generate(String name, String type, boolean mutable) throws IllegalSymbolValueException {
		Symbol newSymbol = null;
		Symbol customSymbol = null;
		
		if(Constants.INT.equals(type)) {
			newSymbol =  new IntegerType(name,null,mutable);
		} 
		
		else if(Constants.FLOAT.equals(type)) {
			newSymbol = new DecimalType(name,null,mutable);
		} 
		
		else if(Constants.STRING.equals(type)) {
			newSymbol =  new StringType(name,null,mutable);
		} 
		
		else if(Constants.BOOLEAN.equals(type)) {
			newSymbol =  new BooleanType(name,null,mutable);
		} 
		
		else if(Constants.CHAR.equals(type)) {
			newSymbol =  new CharType(name,null,mutable);
		}
		
		else if(Constants.FUNCTION.equals(type)) {
			FunctionType newFunction =  null;
			// If name doesn't contain the signature, then simply create a function 
			// and return
			if(Constants.BLANK.equals(name))
				newFunction = new FunctionType(name,mutable,Constants.VOID);
			
			// Else extract the signature from the function name
			else {
				// 1. Extract the name of function and get a new instance
				StringTokenizer tokenizer = new StringTokenizer(name," ,()");
				ArrayList<String> functionDetails = new ArrayList<String>();
				while(tokenizer.hasMoreTokens())
					functionDetails.add(tokenizer.nextToken());
				// 1st element is the function name, 0th element is the return type 
				newFunction =  new FunctionType(functionDetails.get(1),mutable, functionDetails.get(0));
							
				// 2. Extract the name of arguments and set them using SymbolMachine
				// Its an assumption that none of the parameters are constant and hence are mutable.
				for(int argsNo = 2; argsNo < functionDetails.size();argsNo++)
					newFunction.addSymbol(generate(/*functionDetails.get(argsNo + 1)*/Constants.BLANK,functionDetails.get(argsNo),true));
			}
			
			newSymbol = newFunction;
		}
		else if (Constants.POINTER.equals(type)) {
			newSymbol = new PointerType(name, mutable);
		}
		
		else if (Constants.ARRAY.equals(type)) {
			newSymbol = new ArrayType(name,mutable);
		}
		
		else if(Constants.OVERLOADED_FUNCTIONS.equals(type)) {
			newSymbol = new OverloadedFunctionType(name,mutable);
		}
			
		else if(Constants.CLASS.equals(type)) {
			newSymbol = new ClassType(name,mutable);
		}
		
		else if (Constants.NO_TYPE.equals(type)) {
			newSymbol = new ConstantValue(name,Constants.NO_TYPE,mutable);
		}
		
		else if (Constants.VOID.equals(type)) {
			newSymbol = new VoidType(name,mutable);
		}
		
		// If this is a custom data-type then check for the appropriate function/variable type and
        // type-cast and generate the appropriate symbol.
		else {
			try {
				customSymbol = Environment.getSymbol(type);
				
				// If custom symbol is not defined then throw exception
				if(customSymbol == null)
					throw new SymbolNotDefinedException(type);
				
				// Select the custom data-type
				switch(customSymbol.getDataType()) {
				
				case FUNCTION :
					newSymbol = Clone.deepCopySymbol(customSymbol);
					newSymbol.setName(name);
					break;
					
				case POINTER :
					newSymbol = Clone.deepCopySymbol(customSymbol);
					newSymbol.setName(name);
					break;
					
				case ARRAY:
					newSymbol = Clone.deepCopySymbol(customSymbol);
					newSymbol.setName(name);
					break;
					
				case CLASS:
					newSymbol = Clone.deepCopySymbol(customSymbol);
					newSymbol.setName(name);
					break;
					
				default :
					break;
				}

				// Set the mutable status back to whatever it is suppose to have
				newSymbol.setMutableStatus(mutable);
				
			} catch (SymbolNotDefinedException e) {
				System.err.println(e.getMessage());
			} 
		}
		/*else 
			newSymbol =  new CustomType(name,type,mutable);*/
		
		//Environment.globalSymTab.addSymbol(newSymbol);
		return newSymbol;
	}
	
	/**
s	 * Cast the original symbol into data-type that this method receives as a parameter. 
	 * @param originalVariable - Original variable that needs to be type-casted
	 * @param dataType - Data-type to which original variable needs to be type-casted
	 * @return
	 * @throws IllegalSymbolValueException
	 * @throws TypeMismatchException
	 * @throws SymbolNotDefinedException
	 */

	public static Symbol cast(Symbol originalVariable, String dataType) throws IllegalSymbolValueException, TypeMismatchException, SymbolNotDefinedException {
		String name = originalVariable.getName();
		String type = originalVariable.getType();
		boolean mutable = originalVariable.isMutable();
		String strValue = originalVariable.toString();
		
		if(Constants.INT.equals(dataType)) {
			// Since castedSymbol will get a new value, make the mutable status as false. 
			IntegerType castedSymbol = null;
			try {
				castedSymbol =  (IntegerType)SymbolMachine.generate(name, dataType, true);
				if(Constants.FLOAT.equals(type)) 
					castedSymbol.setValue(IntegerType.parseValue(strValue.substring(0, strValue.indexOf("."))));
				else
					castedSymbol.setValue(IntegerType.parseValue(strValue));
			} catch(ImmutableSymbolException e) {}
			// Reset the mutable status symbol to whatever it should be.
			castedSymbol.setMutableStatus(mutable);
			return castedSymbol;
		}
		else if(Constants.FLOAT.equals(dataType)) {
			DecimalType castedSymbol = null;
			try {
				castedSymbol = (DecimalType)SymbolMachine.generate(name, dataType, true);
				castedSymbol.setValue(DecimalType.parseValue(strValue));
			} catch(ImmutableSymbolException e) {}
			// Reset the mutable status symbol to whatever it should be.			
			castedSymbol.setMutableStatus(mutable);
			return castedSymbol;
		}
		else if(Constants.STRING.equals(dataType)) {
			StringType castedSymbol = null;
			try {
				castedSymbol = (StringType)SymbolMachine.generate(name, dataType, true);
				castedSymbol.setValue(StringType.parseValue(strValue));
			} catch(ImmutableSymbolException e) {}
			// Reset the mutable status symbol to whatever it should be.			
			castedSymbol.setMutableStatus(mutable);
			return castedSymbol;
		}
		else if(Constants.BOOLEAN.equals(dataType)) {
			BooleanType castedSymbol = null;
			try {
				castedSymbol =  (BooleanType)SymbolMachine.generate(name, dataType, true);
				castedSymbol.setValue(BooleanType.parseValue(strValue));
			} catch(ImmutableSymbolException e) {}
			// Reset the mutable status symbol to whatever it should be.			
			castedSymbol.setMutableStatus(mutable);
			return castedSymbol;
		}
		else if(Constants.CHAR.equals(dataType)) {
			CharType castedSymbol = null;
			try {
				castedSymbol =  (CharType)SymbolMachine.generate(name, dataType, true);
				castedSymbol.setValue(CharType.parseValue(strValue));
			} catch(ImmutableSymbolException e) {}
			// Reset the mutable status symbol to whatever it should be.			
			castedSymbol.setMutableStatus(mutable);
			return castedSymbol;
		}
		else {
			// This is a custom symbol, so check for the definition of this symbol in Environment
			Symbol customSymbol = null;
			Symbol castedSymbol = null;
			
			customSymbol = Environment.getSymbol(dataType);
			
			// If custom symbol is not defined then throw exception
			if(customSymbol == null)
				throw new SymbolNotDefinedException(dataType);
			
			try {
				// Select the custom data-type
				switch(customSymbol.getDataType()) {
					/* TODO: if type-cast to pointer
					 case POINTER :
						break;*/
					case CLASS:
						String thisClassName = ((ClassType) originalVariable).getClassName();
						ClassType castedClass = (ClassType)Clone.deepCopySymbol(customSymbol);
						// Make the new casted symbol as mutable
						castedClass.setMutableStatus(true);
						// Set the value if castedSymbol is direct descendant of this class
						if(castedClass.isDescendant(thisClassName)) {
							// Not using setValue method, since isCompatible method will 
							// again return false, because LHS is a descendant of RHS
							// castedSymbol.setValue((ClassType)this);
							
							// Assign ancestors
							castedClass.setAncestors(((ClassType)originalVariable).getAncestors());
							// Assign class members
							castedClass.setClassMembers(((ClassType)originalVariable).getClassMembers());
						}
						else
							// else throw exception
							throw new TypeMismatchException(thisClassName, castedClass.getClassName());
						
						// Change the mutable status of the symbol to that of customSymbol
						castedClass.setMutableStatus(mutable);
						
						castedSymbol = castedClass;
						break;
						
					default:
						castedSymbol =  (CustomType)SymbolMachine.generate(name, dataType, true);
						castedSymbol.setValue(originalVariable);
				}
			} catch(ImmutableSymbolException e) {}
			// Reset the mutable status symbol to whatever it should be.			
			castedSymbol.setMutableStatus(mutable);
			return castedSymbol;
		}
	}
	

	/**
	 * This method returns the integer value associated to a variable/constant
	 * @param symbol - Symbol 
	 * @return - int value
	 */
	static int intValue(Symbol symbol) {
		
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
	static float decimalValue(Symbol symbol) {
		
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
	static String stringValue(Symbol symbol) {
		
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
	static boolean booleanValue(Symbol symbol) {
		
		boolean value;
		if(symbol instanceof ConstantValue) 
			value = ((ConstantValue)symbol).booleanValue();
		else
			value = ((BooleanType)symbol).value();
		
		return value;
	}
	
	/**
	 * This method returns the String value associated to a variable/constant
	 * @param symbol - Symbol 
	 * @return - String value
	 */
	static char charValue(Symbol symbol) {
		
		char value;
		if(symbol instanceof ConstantValue) 
			value = ((ConstantValue)symbol).charValue();
		else
			value = ((CharType)symbol).value();
		
		return value;
	}
	
	/**
	 * Returns the function body embedded in symbol
	 * @param symbol
	 * @return
	 */
	static Node functionValue(Symbol symbol) {
		Node functionBody;
		if(symbol instanceof ConstantValue) 
			functionBody = ((ConstantValue)symbol).functionValue();
		else
			functionBody = ((FunctionType)symbol).getFunctionBody();
		
		return functionBody;
	}
	
	/**
	 * Returns the array that is stored in symbol
	 * @param symbol
	 * @return
	 */
	static HashMap<Integer, Symbol> arrayValue(Symbol symbol) {
		HashMap<Integer, Symbol> arrayValue;
		if(symbol instanceof ConstantValue) 
			arrayValue = ((ConstantValue)symbol).arrayValue();
		else
			arrayValue = ((ArrayType)symbol).getArrayValue();
		
		return arrayValue;
	}
	
	/**
	 * Returns the pointer value that is saved in symbol
	 * @param symbol
	 * @return
	 */
	static Symbol pointerValue(Symbol symbol) {
		return ((PointerType) symbol).getPtrValue();
	}
	
	/**
	 * Returns the object that is stored in symbol
	 * @param symbol
	 * @return
	 */
	static ClassType objectValue(Symbol symbol) {
		ClassType classValue = null;
		if(symbol instanceof ConstantValue)
			classValue = ((ConstantValue)symbol).objectValue();
		else
			// No need to get the clone value of symbol. It will be problematic while 
			// assigning one object to another.
			classValue = (ClassType)symbol; //Clone.deepCopySymbol(symbol);
		
		return classValue;
	}
	
	/**
	 * Returns the overloaded function type. Only used for languages where var-declaration
	 * is optional
	 * @param symbol
	 * @return
	 */
	static OverloadedFunctionType overloadedFunctionValue(Symbol symbol) {
		OverloadedFunctionType overloadedFunction = null;
		if(symbol instanceof ConstantValue)
			overloadedFunction = ((ConstantValue)symbol).overloadedFunctionValue();
		else
			overloadedFunction = (OverloadedFunctionType)symbol; 
		
		return overloadedFunction;
	}
	
	/**
	 * This method returns the String value associated to a variable/constant
	 * @param symbol - Symbol 
	 * @return - String value
	 */
	static String userDefinedValue(Symbol symbol) {
		
		String value;
		if(symbol instanceof ConstantValue) 
			value = ((ConstantValue)symbol).userDefinedValue();
		else
			value = ((CustomType)symbol).getValue();
		
		return value;
	}

	
}

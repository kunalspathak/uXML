package compiler.dataTypes;

import java.util.ArrayList;
import java.util.HashMap;

import compiler.components.Environment;
import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;


/**
 * This class represents the list of functions that has same name but different signature.
 * The function name is in 'name' attribute of the function, while value contains the list of 
 * overloaded functions. 'value' is the hashmap of function signature (without the return-type)
 * and function body.
 * @author kunal
 *
 */
public class OverloadedFunctionType extends Symbol {
	
	/**
	 * Represent the mapping of function signature and function body 
	 */
	private HashMap<String, FunctionType> value = null;
	
	/**
	 * Represent the first function that is being set 
	 */
	private FunctionType defaultFunction = null;

	protected OverloadedFunctionType(String name, boolean mutable) {
		super(name, Constants.OVERLOADED_FUNCTIONS, mutable);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getValue() {
		return null;
	}

	@Override
	public void setValue(Symbol symbol) throws IllegalSymbolValueException,
			TypeMismatchException, ImmutableSymbolException {
		if(symbol instanceof FunctionType)
			addFunction(symbol);
		else
			throw new TypeMismatchException(symbol.getType(),Constants.FUNCTION);
	}

	@Override
	public String toString() {
		StringBuffer stringValue = new StringBuffer(name + " : [");
		
		for(String signatures : value.keySet())
			stringValue.append(signatures)
				.append(",");
		
		int lastComma = stringValue.lastIndexOf(","); 
		stringValue.replace(lastComma, lastComma + 1, "]");
		return stringValue.toString();
	}
	
	/**
	 * This method adds the overloaded function in the hashmap, based on its signature.
	 * @param function
	 */
	public void addFunction(Symbol function) {
		FunctionType overloadedFunction = (FunctionType)function;
		// Get the signature of this function
		String signature = overloadedFunction.getSignature();
		
		if(value == null) {
			value = new HashMap<String, FunctionType>();
			defaultFunction = (FunctionType)function;
		}

		// If the signature is already defined, then prompt error stating that the function with given signature
		// is already defined.  The only exception is that this is a lambda function in which case if its present 
		// it is safe to overwrite it, since it was consumed the moment it was added.
		if(!function.getName().equals(Constants.LAMBDA) && value.containsKey(signature)) 
			System.err.println("Function " + function.getName() + signature.toString() + " already defined. ");
		else
			value.put(signature.toString(), overloadedFunction);
	}
	

	/**
	 * @return the defaultFunction
	 */
	public FunctionType getDefaultFunction() {
		return defaultFunction;
	}

	
	/**
	 * This method returns the function that matches the given signature
	 * @param signature - Signature of the this function
	 * @return
	 * @throws SymbolNotDefinedException
	 */
	public FunctionType getFunction(String signature)throws SymbolNotDefinedException {
		if(Constants.NO_TYPE.equals(signature))
			return new FunctionType(Constants.BLANK,true,Constants.VOID);
			
		if(!value.containsKey(signature))
			throw new SymbolNotDefinedException(getName() + signature);
		
		return value.get(signature);
	}
	
	/**
	 * This method returns the function based on type of arguments it takes. 
	 * @param functionArgs
	 * @return
	 * @throws SymbolNotDefinedException
	 */
	public FunctionType getFunction(ArrayList<Symbol> actualArgs) throws SymbolNotDefinedException {
		/* Old implementation....was not taking care if 1 of the argument is OverloadedFunctionType
		 * 
		 * FunctionType func = new FunctionType(Constants.BLANK,false,Constants.VOID);
		for(Symbol arguments : functionArgs)
			func.addSymbol(arguments);
		return  getFunction(func.getSignature());*/
		
		ArrayList<Symbol> formalParams = null;
		boolean match;
		ArrayList<FunctionType> functionList = new ArrayList<FunctionType>();
		functionList.addAll(value.values());
		
		// Iterate over overloaded functions
		for(int functionNo = 0; functionNo < functionList.size(); functionNo++) {
			// Get formal params of current overloaded function
			formalParams = functionList.get(functionNo).getFormalParameters();
			
			// If this function doesn't take any argument, then return it
			if(actualArgs.size() == 0 && formalParams.size() == 0) {
				return functionList.get(functionNo);
			}
			
			// If formal params are not equal to actual arguments then just skip checking for
			// this function and search the next function
			if(formalParams.size() != actualArgs.size())
				continue;
				
			match = false;
			// Iterate over actual arguments that are passed to one of the overloaded function 
			for(int argNo = 0; argNo < actualArgs.size();argNo++) {
				
				// If current argument is an overloaded function, then get appropriate function based on formal parameter's signature
				if(actualArgs.get(argNo) instanceof OverloadedFunctionType) {
					try {
						((OverloadedFunctionType)actualArgs.get(argNo)).getFunction(DataTypeFinder.getDataType(formalParams.get(argNo)));
						match = true;
						// If there is an exception, means actualArgument's function is not overloaded with the signature as
						// that in formal arguments.
					} catch(SymbolNotDefinedException e) {
						match = false;
						break;
					}
				}
				// If actual argument is an object
				else if(actualArgs.get(argNo) instanceof ClassType) {
					// Check if formal parameter is an ancestor of actual argument and if yes, allow it
					if(((ClassType)formalParams.get(argNo)).isAncestor((ClassType)actualArgs.get(argNo)))
							match = true;
				}
				else {
					// If variable declaration is not mandatory, then skip the checking of data-type. Just make sure
					// that the no. of parameters a function takes are same.
					if(!Environment.isVarMandatory);
					// Else if data-type of formal parameters are not as that of actual arguments, then break
					else if(!DataTypeFinder.getDataType(formalParams.get(argNo)).equals(DataTypeFinder.getDataType(actualArgs.get(argNo)))) {
						match = false;
						break;
					}
					match = true;
				}
					
			}
			
			// If the data-type of all arguments matched, then return this function
			if(match)
				return functionList.get(functionNo);
		}
		
		// if nothing matched, then throw exception
		throw new SymbolNotDefinedException("no appropriate signature for '" + name + "'");
	}

}

package compiler.dataTypes;

import java.util.ArrayList;

import org.w3c.dom.Node;

import compiler.components.Environment;
import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SignatureMismatchException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Clone;
import compiler.util.Constants;


/**
 * Represent Symbol of type Function
 * @author kunal
 *
 */
public class FunctionType extends Symbol {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Function body
	 */
	private Node functionBody = null;
	
	/**
	 * This attribute is used to store the signature of a function i.e. formal parameter
	 * data-types whenever the function definition occurs. Once the function is called, 
	 * this symbols will get the values of actual arguments that are passed when the function is 
	 * called and these binded symbols will then be set in activation record. See execute() method
	 * for more details.
	 *  
	 */
	private ArrayList<Symbol> formalParams = null;
	
	/**
	 * This attribute represents the return type of the function
	 */
	private String returnType = "void"; 


	/**
	 * This attribute represents the nested level of this function.
	 */
	private int nestedLevel = -1;
	
	/**
	 * If this function returns another function, then this attribute represents the
	 * function type of that function. Thus it will have value only if attribute returnType
	 * is set to 'function'
	 * 
	 */
//	private FunctionType returnFunctionType = null; 
	
	private Symbol returnValue = null;


	/**
	 * Only functionName will be saved. 
	 * @param name
	 * @param mutable
	 * @param returnType
	 */
	public FunctionType(String name, boolean mutable, String returnType) {
		super(name.trim().length() > 0 ? name : Constants.LAMBDA,Constants.FUNCTION,mutable);
		// If return type is specified then set it, otherwise keep as void
		if(returnType != null && returnType.trim().length() > 0) {
			
			// Create the new symbol of return-type. Also make it mutable, so it can be changed
			try {
				returnValue = SymbolMachine.generate(Constants.BLANK, returnType, true);
				this.returnType = returnValue.getType();
			} catch (IllegalSymbolValueException e) {
				e.printStackTrace();
			} 
		}
		formalParams = new ArrayList<Symbol>();
		
	}
	
	
	/**
	 * @return the returnValue
	 */
	public Symbol getReturnValue() {
		return returnValue;
	}

	/**
	 * @param returnValue the returnValue to set
	 */
	public void setReturnValue(Symbol returnValue) {
		this.returnValue = returnValue;
	}

	
/*	*//**
	 * @return the returnFunctionType
	 *//*
	public FunctionType getReturnFunctionType() {
		return returnFunctionType;
	}*/


/*	*//**
	 * @param returnFunctionType the returnFunctionType to set
	 *//*
	public void setReturnFunctionType(FunctionType returnFunctionType) {
		// Make the clone of returnFunctionType
		this.returnFunctionType = (FunctionType)Clone.deepCopy(returnFunctionType);
		// Make it mutable
		this.returnFunctionType.setMutableStatus(true);
	}*/

	
	
	/**
	 * @return the returnType
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the nestedLevel
	 */
	public int getNestedLevel() {
		return nestedLevel;
	}

	/**
	 * @param nestedLevel the nestedLevel to set
	 */
	public void setNestedLevel(int nestedLevel) {
		this.nestedLevel = nestedLevel;
	}

	@Override
	public String getValue() {
		return toString();
	}

	@Override
	public void setValue(Symbol symbol) throws IllegalSymbolValueException,
			TypeMismatchException, ImmutableSymbolException {
		// If current symbol is not mutable, then thrown the exception
		if(!isMutable())
			throw new ImmutableSymbolException(name);
		
		// If this is overloaded function list, then get the appropriate function
		if(symbol instanceof OverloadedFunctionType) {
			String required = null;
			FunctionType function = null;
			try {
				required = getSignature();
				// Get the appropriate function depending on the signature
				function = ((OverloadedFunctionType)symbol).getFunction(required);
				if(function == null)
					throw new SymbolNotDefinedException();
				else
					symbol = function;
			} catch (SymbolNotDefinedException e) {
				throw new TypeMismatchException("no appropriate signature for " + symbol.getName(), symbol.getName() + required);
			}
		}
		
		if(this.getDataType() == symbol.getDataType()) {
			this.functionBody = SymbolMachine.functionValue(symbol);
			this.nestedLevel = ((FunctionType)symbol).nestedLevel;
			this.formalParams = ((FunctionType)symbol).formalParams;
			this.returnType = ((FunctionType)symbol).returnType;
			//this.returnFunctionType = ((FunctionType)symbol).returnFunctionType;
//			this.name = ((FunctionType)symbol).name;
			this.returnValue = ((FunctionType)symbol).returnValue;
		}
		else
			throw new TypeMismatchException(symbol.getType(), this.getType());
		// Not needed for function type

	}
	

	/**
	 * This method performs the calling sequence of binding the actual arguments to the
	 * formal parameters. Then it returns the function body. 
	 * @return
	 * @throws TypeMismatchException 
	 * @throws IllegalSymbolValueException 
	 * @throws SignatureMismatchException 
	 */
	public Node execute(ArrayList<Symbol> actualArgs) throws IllegalSymbolValueException, TypeMismatchException, SignatureMismatchException {
		
		// Bind the actual arguments to the formal parameters.
		if(!isSignatureSame(actualArgs)) {
			StringBuffer functionSignature = new StringBuffer(name + getSignatureOf(actualArgs));
			/*if(actualArgs.size() > 0) {
				// Fill in the formal argument's type
				functionSignature.append(actualArgs.get(0) != null ? actualArgs.get(0).getType() : "null");
				for(int argsNo = 1; argsNo < actualArgs.size(); argsNo++) {
					functionSignature.append(",")
						.append(actualArgs.get(argsNo) != null ? actualArgs.get(argsNo).getType() : "null");
				}
			}
			functionSignature.append(")");*/
			throw new SignatureMismatchException(this,functionSignature.toString()); 
		}
		
		// System.out.println("Executing " + this.toString());
		// Bind the actual argument values to formal parameters of a function
		// If one of the formal parameter is declared as constant, the code below might 
		// give an exception ImmutableSymbolException. However for such symbols, it should 
		// allow the actual arguments to set the value and then never change.
		for(int argsNo = 0; argsNo < actualArgs.size();argsNo++) {
			
			try {
				Symbol formalArgument = formalParams.get(argsNo);
				// If formal arguments is array, then copy it directly instead of calling setValue()
				// since setValue will set the actualArgs.get(argsNo) value in each entry of array represented
				// by formalArgument.
				if(formalArgument instanceof ArrayType)
					((ArrayType) formalArgument).copyArray(actualArgs.get(argsNo));
				else
					formalArgument.setValue(actualArgs.get(argsNo));
			} catch (ImmutableSymbolException e) {
				// Make the formal parameter as mutable
				formalParams.get(argsNo).makeMutable();
				
				// Again set the value
				try { formalParams.get(argsNo).setValue(actualArgs.get(argsNo)); } catch(ImmutableSymbolException ee){}
				
				// Make the formal parameter as immutable
				formalParams.get(argsNo).makeImmutable();

			}
		}
		
		String symbolType = null;
		// Store the formalParams in symbol table of current activation record.
		for(Symbol symbol : formalParams) {
			// If class definition then add the symbols in current class
			// SymbolNotDefinedException will never occur for parameters, it is just
			// throw for constructor
			// Make the clone so that changing formal params doesn't affect actual symbols present in activation record
			// However if the symbol is pointer-type, then copy as it is, since we want the symbol to get affected 
			// in the function body
			try {
				// If var declaration is not mandatory and the symbol is of constant value then simply extract
				// the data-type
				if(!Environment.isVarMandatory && symbol instanceof ConstantValue)
					symbolType = symbol.getType();
				else
					symbolType = DataTypeFinder.getDataType(symbol);
			} catch (SymbolNotDefinedException e) {}
			
			// If symbolType is a pointer, then copy the symbol as it is  - a quick fix
			if(Environment.isClassDefinition)
				((ClassType)Environment.currentClass).addSymbol(symbolType.contains("*")/*symbol instanceof PointerType*/? symbol : Clone.deepCopySymbol(symbol));   
			else
				Environment.currentActivationRecord.addSymbol(symbolType.contains("*")/*symbol instanceof PointerType*/? symbol : Clone.deepCopySymbol(symbol));
		}

		// return the function body
		return functionBody;
	}

	
	/**
	 * This method sets the function body of this function. It also sets the signature of
	 * this function as this function's type.
	 * Setting the signature is assumed, since it is assumed that addSymbol() method is called
	 * before calling this method so that the data-types of formal arguments can be determined
	 * while setting the function-type.
	 * @param functionBody the functionBody to set
	 */
	public void setFunctionBody(Node functionBody) {
		this.functionBody = functionBody;
		
		// Set the signature of this function as the 'type' attribute
		// type = toString();
	}
	
	
	/**
	 * Returns the function body
	 * @return
	 */
	public Node getFunctionBody(){
		return this.functionBody;
	}
	
	public ArrayList<Symbol> getFormalParameters() {
		return this.formalParams;
	}
	
	public void clearFormalParameters() {
		this.formalParams = new ArrayList<Symbol>();
	}
	
	/**
	 * This method is used to add formal parameter symbols at the time of function
	 * definition.
	 * @param symbol - Symbol to be added in symbol table.
	 */
	public void addSymbol(Symbol symbol) {
		formalParams.add(symbol);
	}

	@Override
	public String toString() {
		StringBuffer functionSignature = new StringBuffer();
		//functionSignature.append("-----------------");
		functionSignature.append(returnType)
			.append(" ")
			.append(name)
			.append(getSignature());
		
		/*if(formalParams.size() > 0) {
			// Fill in the formal argument's type
			functionSignature.append(formalParams.get(0).getType());
			for(int argsNo = 1; argsNo < formalParams.size(); argsNo++) {
				functionSignature.append(",")
					.append(formalParams.get(argsNo).getType());
			}
		}
		functionSignature.append(")");*/
		//functionSignature.append(")-----------------");
		return functionSignature.toString();
	}
	
	/**
	 * This method checks if current function's signature and name is same as that of the
	 * one received as a parameter. The method is used if there is an occurrence of method
	 * overload with same signature.
	 * The method can also be used to match the function call among the set of functions having
	 * same name but different signature.
	 * 
	 * @param function
	 * @return - 'true' if name and signature of the function is same, else 'false'. 
	 */
	public boolean isEqual(FunctionType function) {
		
		if(!name.equals(function.getName()))
			return false;
		else
			return isSignatureSame(function.formalParams);
	}
	
	/**
	 * This method checks if current function's signature is same as that of the
	 * one received as a parameter. The method is used if there is an occurrence of method
	 * overload with same signature.
	 * The method can also be used to match the function call among the set of functions having
	 * same name but different signature.
	 * 
	 * @param functionArgs
	 * @return
	 */
	public boolean isSignatureSame(ArrayList<Symbol> functionArgs) {
		if(formalParams.size() != functionArgs.size())
			return false;
		
		// If variable declaration is not mandatory, then return
		// No need to check the exact data-types of the arguments.
		if(!Environment.isVarMandatory)
			return true;
		
		if(getSignature().equals(getSignatureOf(functionArgs)))
			return true;
		else
			return false;
		/*
		 * Old way....was not considering if actual arguments contain overloadedFunctionType
		 * // Check the data-type of each symbol present in the list of function arguments.
		for(int argsNo = 0; argsNo < functionArgs.size();argsNo++) {
			
		
			try {
				if(formalParams.get(argsNo) == null || functionArgs.get(argsNo) == null ||
						!DataTypeFinder.getDataType(formalParams.get(argsNo)).equals(DataTypeFinder.getDataType(functionArgs.get(argsNo))))
					return false;
			} catch (SymbolNotDefinedException e) {
				return false;
			}
		}
		return true;*/
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String functionName) {
		this.name = functionName;
	}
	
	/**
	 * This method generates the signature based on the list of actual arguments 'actualArgs' that is passed
	 * as an argument. In case one of the parameter is overloaded function as one of the actual arguments, 
	 * the conflict is resolved by checking the signature of overloaded function in list of formal arguments 'formalArgs'. 
	 * @param actualArgs - List of actual arguments that are passed to the function.
	 * @return
	 */
	public String getSignatureOf(ArrayList<Symbol> actualArgs) {
		
		StringBuffer functionSignature = new StringBuffer("(");
		Symbol functionArgument = null;
		FunctionType overloadedFunction = null;
		try {
			if(actualArgs.size() > 0 && (functionArgument = actualArgs.get(0)) != null) {
				
				if(functionArgument instanceof OverloadedFunctionType) {
					// If this is an overloaded function, then get the appropriate function depending on the signature
					// that is stored in formalArgs list.
					overloadedFunction = ((OverloadedFunctionType)functionArgument).getFunction(DataTypeFinder.getDataType(formalParams.get(0)));
					functionSignature.append(DataTypeFinder.getDataType(overloadedFunction));
				}
				
				// If actual argument is an object
				else if(functionArgument instanceof ClassType) {
					// Check if formal parameter is an ancestor of actual argument and if yes, allow it
					if(((ClassType)formalParams.get(0)).isAncestor((ClassType)functionArgument))
						functionSignature.append(DataTypeFinder.getDataType((ClassType)formalParams.get(0)));
				}
				// Fill in the formal argument's type
				else
					functionSignature.append(functionArgument != null ? DataTypeFinder.getDataType(functionArgument) : "null");
				
				for(int argsNo = 1; argsNo < actualArgs.size(); argsNo++) {
					functionArgument = actualArgs.get(argsNo);
					functionSignature.append(",");
					if(functionArgument instanceof OverloadedFunctionType) {
						// If this is an overloaded function, then get the appropriate function depending on the signature
						// that is stored in formalArgs list.
						overloadedFunction = ((OverloadedFunctionType)functionArgument).getFunction(DataTypeFinder.getDataType(formalParams.get(argsNo)));
						functionSignature.append(DataTypeFinder.getDataType(overloadedFunction));
					}
					// If actual argument is an object
					else if(functionArgument instanceof ClassType) {
						// Check if formal parameter is an ancestor of actual argument and if yes, allow it
						if(((ClassType)formalParams.get(argsNo)).isAncestor((ClassType)functionArgument))
							functionSignature.append(DataTypeFinder.getDataType((ClassType)formalParams.get(argsNo)));
					}
					// Fill in the formal argument's type
					else
						functionSignature.append(DataTypeFinder.getDataType(functionArgument));
				}
			}
			functionSignature.append(")");
		} catch(SymbolNotDefinedException e) {
			functionSignature = new StringBuffer("null");
		}
		
		/*if(functionArgs.size() > 0) {
			// Fill in the formal argument's type
			functionSignature.append(functionArgs.get(0) != null ? functionArgs.get(0).getType() : "null");
			for(int argsNo = 1; argsNo < functionArgs.size(); argsNo++) {
				functionSignature.append(",")
					.append(functionArgs.get(argsNo) != null ? functionArgs.get(argsNo).getType() : "null");
			}
		}*/

		
		return functionSignature.toString();
	}
	
	
 	/**
	 * This method generates the signature based on the formal parameters of this function. 
	 * @param formalArgs - List of formal arguments that are passed to the function.
	 * @return
	 */
	
	public String getSignature() {
		
		StringBuffer functionSignature = new StringBuffer("(");
		Symbol functionArgument = null;
		try {
			if(formalParams.size() > 0 && (functionArgument = formalParams.get(0)) != null) {
				
				functionSignature.append(DataTypeFinder.getDataType(functionArgument));
				for(int argsNo = 1; argsNo < formalParams.size(); argsNo++) {
					functionArgument = formalParams.get(argsNo);
					functionSignature.append(",")
						.append(DataTypeFinder.getDataType(functionArgument));
				}
			}
		} catch(SymbolNotDefinedException e) {
			System.err.println(e.getMessage());
		}


		functionSignature.append(")");
		return functionSignature.toString();
	}
	 
	
 	/**
	 * This method generates the signature based on the formal parameters of this function. 
	 * @param formalArgs - List of formal arguments that are passed to the function.
	 * @return
	 */
	
	public static String getSignature(ArrayList<Symbol> arguments) {
		
		StringBuffer functionSignature = new StringBuffer("(");
		Symbol functionArgument = null;
		try {
			if(arguments.size() > 0 && (functionArgument = arguments.get(0)) != null) {
				
				functionSignature.append(DataTypeFinder.getDataType(functionArgument));
				for(int argsNo = 1; argsNo < arguments.size(); argsNo++) {
					functionArgument = arguments.get(argsNo);
					functionSignature.append(",")
						.append(DataTypeFinder.getDataType(functionArgument));
				}
			}
		} catch(SymbolNotDefinedException e) {
			System.err.println(e.getMessage());
		}


		functionSignature.append(")");
		return functionSignature.toString();
	}
	
}

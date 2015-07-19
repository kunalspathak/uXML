package compiler.components;

import java.io.Serializable;
import java.util.HashMap;

import compiler.dataTypes.FunctionType;
import compiler.dataTypes.OverloadedFunctionType;
import compiler.dataTypes.SymbolMachine;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.util.Constants;



/**
 * This class represents the symbol table that can be used to store the
 * symbols that are read during parsing. Symbols could be anything from
 * variables, procedures or list of overloaded functions. 
 * @author kunal
 *
 */
public class SymbolTable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<String,Symbol> storage = null;
	
	
	/**
	 * Initializes a symbol table that maps symbol name with actual symbol 
	 */
	public SymbolTable() {
		storage = new HashMap<String, Symbol>();
	}
	
	/**
	 * This method adds the given symbol in a symbol table.
	 * 
	 * @param symbol to be added in symbol table.
	 */
	public void addSymbol(Symbol symbol) {
		if(symbol != null) {
			
			/*
            * Changes for Overloaded function - start
            */
			// If this is a function, store the signature instead of name
			// It should be Function and not FunctionType 
			//TODO : Change the check criteria for function-type from nested level to something else
			if(symbol instanceof FunctionType && ((FunctionType)symbol).getNestedLevel() > -1) {
				Symbol listOfFunctions = storage.get(symbol.getName());
				// If not already created, then create the overloadedFunction list for this function
				if(listOfFunctions == null)
					try {
						listOfFunctions = SymbolMachine.generate(symbol.getName(), Constants.OVERLOADED_FUNCTIONS, symbol.isMutable());
					} catch (IllegalSymbolValueException e) {} 
					
				// Add the function in listOfFunctions that is present for this symbol name
				((OverloadedFunctionType)listOfFunctions).addFunction(symbol);
				symbol = listOfFunctions;
			}
			/*
             * Changes for Overloaded function - end
             */
			storage.put(symbol.getName(), symbol);
		}
	}
	
	
	/**
	 * This method returns the symbol associated to given symbol name.
	 * @param symbolName
	 * @return - symbol
	 * @throws SymbolNotDefinedException
	 */
	public Symbol getSymbol(String symbolName) throws SymbolNotDefinedException {
		if(!isSymbolPresent(symbolName))
			throw new SymbolNotDefinedException(symbolName);
		return storage.get(symbolName);
	}

	/**
	 * This method removes the symbolName from the storage. This method is currently
	 * used to delete the system-generated constructor.
	 * @param symbolName
	 */
	public void removeSymbol(String symbolName) {
		storage.remove(symbolName);
	}
	
	/**
	 * This method is used to peek into the symbol table of the given symbol 
	 * is present or not.
	 * @param symbolName
	 * @return
	 */
	public boolean isSymbolPresent(String symbolName) {
		return storage.containsKey(symbolName);
	}
	
	@Override
	public String toString() {
		StringBuffer symbols = new StringBuffer("");
		for(Symbol sym : storage.values())
			symbols.append(sym.toString()).append(",");
		return symbols.substring(0, symbols.length() - 1);
	}
	
}

package compiler.components;

import java.util.ArrayList;

import compiler.exceptions.SymbolNotDefinedException;


/**
 * This class represents the activation record that is required to implement 'functions'
 * feature of a programming language.
 * The class can be used for both frontend as well as backend.
 * For frontend, this acts as a symbol table for current function that is being parsed. The 
 * variable access can be then checked with those present in this activation record. If the 
 * variable to be accessed in source language is not present in the list of variables, then 
 * throw CompilationException.
 * For backend, this class acts as the actual activation record. 
 * @author kunal
 *
 */
public class ActivationRecord {
	

	/**
	 * Return value
	 */
	private Symbol returnValue = null;

	/**
	 * Stores formal parameters as well as local variables.

	private SymbolTable variables = null;
	*/

	/**
	 * Defines the block level scoping of variables. Each new block will add a
	 * new symbol table to the next available spot in variablesInBlock arraylist and
	 * while searching, the arraylist will be searched in reverse direction so that current
	 * block is search first, followed by enclosing block and so on.
	 * Last entry of an array list will be removed as soon as block is ended.
	 */
	private ArrayList<SymbolTable> variablesInBlocks = null;

	/**
	 * Used to store the activation record of statically enclosed procedure/function
	 * to implement static scoping to access non-local parameters.
	 */
	// private ActivationRecord staticAccessLink = null;

	/**
	 * Used to store the activation record of caller in order to implement the
	 * dynamic scoping to access non-local parameters.
	 */
	private ActivationRecord dynamicAccessLink = null;

	/**
	 * Represents the nested level of current procedure.
	 */
	private int nestedLevel = -1;

	/**
	 * This variable specifies if return statement has been encountered or not. This attribute
	 * makes sure that if current function returns the value if it assures of returning at the
	 * time of definition.
	 */
	private boolean returnsValue = false;



	/**
	 * Initialize the fields of activation record
	 */
	public ActivationRecord() {
		// Initialize the symbol table
		/*variables = new SymbolTable();*/
		variablesInBlocks = new ArrayList<SymbolTable>();
	}

	/**
	 * This method will be called just before entering a new block scope. The method
	 * adds a new symbol table to the list. Variables defined in the block will be added in new symbol table that
	 * is dedicated for current block.
	 */
	public void enterTheBlock() {
		variablesInBlocks.add(new SymbolTable());
	}

	/**
	 * This method will be called just after leaving the block scope. The method removes the symbol table
	 * of the block which just ended.
	 */
	public void leaveTheBlock() {
		variablesInBlocks.remove(lastIndexOfNestedBlocks());
	}

	private int lastIndexOfNestedBlocks() {
		return variablesInBlocks.size() - 1;
	}


	/**
	 * @return the returnValue
	 */
	public Symbol getReturnValue() {
		return returnValue;
	}


	/**
	 * @param returnValue - the returnValue to set
	 * @param returnsValue - Specifies if this method was called by return statement or not. It is useful if
	 * any method was suppose to return a value, but it doesn't do it.
	 */
	public void setReturnValue(Symbol returnValue) {
		this.returnValue = returnValue;
	}


	/**
	 * @return the returnsValue
	 */
	public boolean isReturnsValue() {
		return returnsValue;
	}


	/**
	 * @param returnsValue the returnsValue to set
	 */
	public void setReturnsValue(boolean returnsValue) {
		this.returnsValue = returnsValue;
	}


	/**
	 * This method is used to extract the symbol present in this activation
	 * record. If SymbolNotDefinedException is thrown, it means that this is a non-local
	 * parameter access and hence depending on static/dynamic scoping, appropriate
	 * activation record is looked-up through display or dynamic access links.
	 *
	 * Block scoping - The symbol is search in all the blocks starting from current active block
	 * and if not found continue the search in enclosing block. If symbol is not found in any of the
	 * enclosing blocks, SymbolNotDefinedException is thrown.
	 *
	 * @return the variables
	 * @throws SymbolNotDefinedException
	 */
	public Symbol getSymbol(String symbolName) throws SymbolNotDefinedException {
		/*return variables.getSymbol(symbolName);*/
		Symbol symbolFound = null;
		for(int currentBlockDepth = lastIndexOfNestedBlocks();currentBlockDepth >= 0; currentBlockDepth--) {
			try {
				symbolFound = variablesInBlocks.get(currentBlockDepth).getSymbol(symbolName);
				if(symbolFound  != null) break;
			} catch (SymbolNotDefinedException e) {}
		}
		if(symbolFound == null)
			throw new SymbolNotDefinedException(symbolName);
		else
			return symbolFound;
	}

	/**
	 * This method is used to add a symbol in current activation record. This method will
	 * be called at the time of function execution, whenever there are any variable declaration.
	 *
	 * Block scope - Add the symbol in current active block's symbol table
	 *
	 * @param symbol - symbol to be added in the symbol table.
	 */
	public void addSymbol(Symbol symbol) {
		/*variables.addSymbol(symbol);*/
		variablesInBlocks.get(lastIndexOfNestedBlocks()).addSymbol(symbol);
	}

	/**
	 * This method checks if symbol having symbolName is present in the symbol table
	 * of current activation record or not. This method is useful to see if there are
	 * any non-local parameter access within the procedure.
	 * @param symbolName
	 * @return
	 */
	public boolean isSymbolPresent(String symbolName){
		/*return variables.isSymbolPresent(symbolName);*/
		for(int currentBlockDepth = lastIndexOfNestedBlocks();currentBlockDepth >= 0; currentBlockDepth--) {
				if(variablesInBlocks.get(currentBlockDepth).isSymbolPresent(symbolName))
					return true;
		}
		return false;
	}

	/**
	 * @return the dynamicAccessLink
	 */
	public ActivationRecord getDynamicAccessLink() {
		return dynamicAccessLink;
	}

	/**
	 * @param dynamicAccessLink the dynamicAccessLink to set
	 */
	public void setDynamicAccessLink(ActivationRecord dynamicAccessLink) {
		this.dynamicAccessLink = dynamicAccessLink;
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
	public String toString() {
		/*return variables.toString();*/
		return variablesInBlocks.toString();
	}



}

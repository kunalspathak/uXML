package compiler.components;

import java.util.Stack;

import compiler.dataTypes.ClassType;
import compiler.dataTypes.ConstantValue;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.util.Constants;



public class Environment {
	
	/**
	 * Symbol Table to store the global variables of a program.
	 * Currently it is not handling class-level global variables.
	 */
	public static SymbolTable globalSymTab = null;
	
	/**
	 * Activation record of a current procedure that is getting executed.  
	 */
	public static ActivationRecord currentActivationRecord = null;
	
	/**
	 * Runtime stack that stores the activation record whenever there are method calls.
	 */
	public static Stack<ActivationRecord> runTimeStack = null;
	
	/**
	 * Display data-structure to handle the static-scoping.
	 */
	public static Display display = null;
	
	/**
	 * Implementation of calling sequence. This value would come from client side.
	 */
	public static CallingSequence callSequenceProcessor = null;
	
	/**
	 * Type of scope. Either static or dynamic
	 */
	public static String scopeType = Constants.STATIC;
	
	/**
	 * Variable declaration mandatory or not.
	 */
	public static boolean isVarMandatory = true;
	
	/**
	 * The variable is used for creating the dynamic type variable
	 */
	private static boolean createDynamicVariable = false;
	
	/**
	 * If this is the class definition or not
	 */
	public static boolean isClassDefinition = false;
	
	/**
	 * Current class that is getting defined 
	 */
	public static ClassType currentClass = null;
	
	/**
	 * Is Polymorphism allowed or not
	 */
	public static boolean isPolymorphism = false;
	
	
	/**
	 * Initialize the global fields
	 *  
	 * @param callSequence - Gets the implementation of calling sequence
	 */
	public static void initialize(CallingSequence callSequence, boolean isDynamicVariable) {
		// Initialize global symbol table
		globalSymTab = new SymbolTable();
		
		// Set create Dynamic variable flag
		createDynamicVariable = isDynamicVariable;
		
		// Initializes current activation record
		currentActivationRecord = new ActivationRecord();
		currentActivationRecord.setNestedLevel(0);
		
		// Initialize the run time stack
		runTimeStack = new Stack<ActivationRecord>();
		// Push the current Activation record in runtime stack 
		runTimeStack.push(currentActivationRecord);
		
		// Initialize the display
		display = Display.instance();
		// Make an entry of current activation record in diplay 
		display.insertActivationRecord(0, currentActivationRecord);
		
		// Assign the implementation of calling sequence
		callSequenceProcessor = callSequence;
		
	}
	
	/**
	 * This method searches for the symbol specified by symbolName argument
	 * in the activation record depending on static/dynamic scope and return
	 * it.
	 * @param symbolName
	 * @return
	 * @throws SymbolNotDefinedException 
	 */
	public static Symbol getSymbol(String symbolName) throws SymbolNotDefinedException {
		/*
		 * Old Code ....  before introducing class-type
		 * if(Constants.STATIC.equals(Environment.scopeType))
            return Environment.callSequenceProcessor.staticScopeAccess(symbolName);
        else
            return Environment.callSequenceProcessor.dynamicScopeAccess(symbolName);*/
		
		Symbol symbolFound = null; 
		SymbolNotDefinedException symbolNotFound = null;
		try {
			// Object Oriented programs will implement static scope
			if(Constants.STATIC.equals(Environment.scopeType) || Environment.isClassDefinition)
				symbolFound = Environment.callSequenceProcessor.staticScopeAccess(symbolName);
	        else
	        	symbolFound = Environment.callSequenceProcessor.dynamicScopeAccess(symbolName);
		} catch(SymbolNotDefinedException e) {symbolNotFound = e;}
		
		try {
			// If this is the usage in class member function, then check if it is present in current
			// class or not.
			if(symbolFound == null && Environment.isPolymorphism && Environment.currentClass != null)
				symbolFound = Environment.currentClass.getSymbol(symbolName);
			
		} catch(SymbolNotDefinedException e) { symbolNotFound = e; }
		
		// If symbol is still not found, then search in global symbol table
		if(symbolFound == null && globalSymTab.isSymbolPresent(symbolName)) {
			symbolFound = globalSymTab.getSymbol(symbolName);
		}
		
		// If symbol not found and var-declaration is not mandatory and if need to create a variable 
		// then create a symbol, insert it in currentActivationRecord and return it.
		if(symbolFound == null && !isVarMandatory && createDynamicVariable) {
			symbolFound = new ConstantValue(symbolName,Constants.NO_TYPE,true);
			currentActivationRecord.addSymbol(symbolFound);
		}
		
		// If symbol is still not found, then throw the exception
		if(symbolFound == null)
			throw symbolNotFound;
	
		
		return symbolFound;
	}
	
	/**
	 * This method adds the global variables in the global symbol table
	 * @param symbol
	 */
	public static void addGlobalSymbol(Symbol symbol) {
		globalSymTab.addSymbol(symbol);
	}
	
	/**
	 * Checks if symbol is present in global scope or not
	 * @param symbolName
	 * @return
	 */
	public static boolean isSymbolPresentInGlobalScope(String symbolName) {
		return globalSymTab.isSymbolPresent(symbolName);
	}
	
	public static Symbol createSymbol(String symbolName) throws SymbolNotDefinedException {
		// Make the flag of creationg variable 'true'
		createDynamicVariable = true;
		Symbol symbol = getSymbol(symbolName);
		// Make the flag of creationg variable 'false'
		createDynamicVariable = false;
		return symbol;
	}
}

package compiler.dataTypes;

import java.util.ArrayList;

import compiler.components.Environment;
import compiler.components.Symbol;
import compiler.components.SymbolTable;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.uXMLGenerator.UXMLOOCodeGenerator;
import compiler.util.Clone;
import compiler.util.Constants;


public class ClassType extends Symbol {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Represents the symbol table of class member variables
	 */
	private SymbolTable classMembers = null;
	
	/**
	 * Represents the list of ancestor, if any. 
	 */
	private ArrayList<ClassType> ancestors = null;

	/**
	 * Represent the class name of this object
	 */
	private String className = null;
	
	/**
	 * Before every usage, checks if it's initialized or not
	 */
	private boolean isInitialized = false;
	
	/**
	 * This attribute specifies if user has defined any constructor or not. 
	 * On initialization of class, default zero parameter constructor will be created
	 * but once user defines the constructor(having any no. of parameters) the default
	 * system defined constructor will be deleted.
	 * Initially user-constructor is not defined 
	 */
	private boolean isConstructorDefined = false;
	
	
	/**
	 * @param name - Same as class-name in case this is representing a class. If this is an instance, then 'name' would
	 * be the name of this instance. For class-definition, setClassName() method should be explicitly called in order to 
	 * set the class name.
	 * Eg. HelloWorld obj = null; 
	 * 		<var name="obj"  type="HelloWorld" mutable="true"/>
	 * For this case, className attribute will be 'HelloWorld' and name will be 'obj' 
	 * @param type
	 * @param mutable
	 */
	protected ClassType(String name, boolean mutable) {
		super(name, Constants.CLASS, mutable);
		
		// Initialize the class members symbol table
		classMembers = new SymbolTable();
		
		// Initialize the list of ancestors
		ancestors = new ArrayList<ClassType>();
		
		// Create a default constructor
		FunctionType constructor = new FunctionType(Constants.CONSTRUCTOR,mutable,Constants.VOID);
		constructor.setFunctionBody(UXMLOOCodeGenerator.generateConstructor());
		// Set nested-level to zero so that it is added in overloaded functions and is not treated like normal symbol
		// TODO: Once this clause is fixed, the setting of nested level will no more required.
		constructor.setNestedLevel(0);
		classMembers.addSymbol(constructor);
		
	}
	
	
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}


	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @param classMembers the classMembers to set
	 */
	public void setClassMembers(SymbolTable classMembers) {
		this.classMembers = classMembers;
	}


	/**
	 * @param ancestors the ancestors to set
	 */
	public void setAncestors(ArrayList<ClassType> ancestors) {
		this.ancestors = ancestors;
	}

	/**
	 * @return the classMembers
	 */
	public SymbolTable getClassMembers() {
		return classMembers;
	}


	/**
	 * @return the ancestors
	 */
	public ArrayList<ClassType> getAncestors() {
		return ancestors;
	}


	@Override
	public String getValue() {
		return null;
	}

	@Override
	public void setValue(Symbol symbol) throws IllegalSymbolValueException,
			TypeMismatchException, ImmutableSymbolException {

		// If current symbol is not mutable, then thrown the exception
		if(!isMutable())
			throw new ImmutableSymbolException(name);
		
		ClassType rhsObject = SymbolMachine.objectValue(symbol);

		// This method sets the value only if lhs is an ancestor of rhs
		if(isAncestor(rhsObject)) {
			// Assign ancestors only if lhs and rhs are of same level
			// If lhs is an ancestor of rhs, then don't assign rhs's ancestors to lhs
			// if(className.equals(rhsObject.className))
			
			this.ancestors = rhsObject.ancestors;
			
			// If this is a class, this is an initialization of an object, hence 
			// give the new set of class member variables.
			// However if this is an object, then assign the class members as it is so that
			// assignment will reflect pointer assignment.
			if(rhsObject.isClass()) {
				this.classMembers = Clone.deepCopySymbolTable(rhsObject.classMembers);
				// Since rhsObject is a class, it is returned from constructor call hence this object is initialized.
				this.isInitialized = true;
			}
			else {
				this.classMembers = rhsObject.classMembers;
				// make initialization as true, only if object that is getting assigned is true
				isInitialized = rhsObject.isInitialized;
			}
		} else
			throw new TypeMismatchException(rhsObject.className, className);
	}
	
	/**
	 * Checks if lhs is an ancestor of rhsObject 
	 * @param rhsObject
	 * @return
	 */
	public boolean isAncestor(ClassType rhsObject) {
		
		// Get the class name of symbol that needs to be assigned to this object
		String rhsClassName = rhsObject.className;
		
		// If this object is of same type as that of symbol that is passed, then assign
		// the symbol to this object
		if(className.equals(rhsClassName)) {
			return true;
			 
		}
		// If rhsObject can be a descendant of this object, so check the ancestors
		else 
		{
			for(ClassType ancestor : rhsObject.ancestors) {
				// Try setting the value to one of the ancestor 
				if(isAncestor(ancestor) == true) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * This method takes the class name and checks if this object is an instance or descendant of parent class name
	 * that is passed as an argument
	 * @param parentClassName
	 * @return
	 */
	public boolean isDescendant(String parentClassName) {
		// If this object is of same type as that of parentClassName 
		// then return true
		if(className.equals(parentClassName)) {
			return true;
			 
		}
		// Else check if ancestors of this object are descendant of parentClassName
		else 
		{
			for(ClassType ancestor : ancestors) {
				// Check if the ancestor is descendant of parentClassName 
				if(ancestor.isDescendant(parentClassName) == true) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public String toString() {
		return className + ":" + name;
	}
	
	/**
	 * This method searches for given function that matches to the given actual arguments type
	 * and if not found searches up in the hierarchy until found.
	 * @param function
	 * @param actualArgs
	 * @return
	 * @throws SymbolNotDefinedException 
	 */
	public FunctionType lookupFunction(String functionToFind, ArrayList<Symbol> actualArgs) throws SymbolNotDefinedException {
		FunctionType function = null;
		boolean found = false;
		
		
		// If this is a constructor call, then instantiate the object
		if(!name.equals(className) && Constants.CONSTRUCTOR.equals(functionToFind)) {
			isInitialized = true;
		}
		
		// If this object is used before initialization (since check is just for object, if this is 
		// an access to class-level function then skip the check.) then prompt error
		if(!isInitialized && !name.equals(className)) {
			throw new SymbolNotDefinedException("Usage of '" + toString() + "' without initialization");
		}

		
		while(!found) {
			// Check the function in this Class
			// If it is a call to super() then don't search in this constructor
			if(!Constants.SUPER_CLASS.equals(functionToFind) && classMembers.isSymbolPresent(functionToFind)) {
				try {
					// Get the list of overloaded function depending on functionToFind and in that list, check
					// for function that matches actual arguments
					function = ((OverloadedFunctionType) classMembers.getSymbol(functionToFind)).getFunction(actualArgs);
					Environment.currentClass = this;
				} catch (SymbolNotDefinedException e) {}
			}
			// Check if this is a call to super-class's constructor, 
			// if yes, check the constructor only in ancestors
			else if(Constants.SUPER_CLASS.equals(functionToFind)) {
				functionToFind = Constants.CONSTRUCTOR;
			}
			
			// If function is not present in current class, then search in ancestor classes.
			if(function == null) {
				for(int ancestorNo = 0; ancestorNo < ancestors.size();ancestorNo++) {
					
					function = ancestors.get(ancestorNo).lookupFunction(functionToFind, actualArgs);
					
					// If found, then break the loop, else keep searching
					// also if found, then modify the currentClass to one that is matched, so that 
					// it can access ancestor's variables.
					if(function != null) {
						Environment.currentClass = ancestors.get(ancestorNo);
						found = true; 
						break; 
					}
				}
				
				if(!found)
					throw new SymbolNotDefinedException(className  + "." + functionToFind + FunctionType.getSignature(actualArgs) + " is not defined.");
			}
			else found = true;
		}
		
		return function;
	}
	
	/**
	 * Add class variable, Constructor of function to this Class
	 * @param symbol
	 * @throws SymbolNotDefinedException 
	 */
	public void addSymbol(Symbol symbol)  {
		classMembers.addSymbol(symbol);
	}
	
	/**
	 * This method adds a new constructor to the current class
	 * @param constructor
	 */
	public void addConstructor(FunctionType constructor, ArrayList<Symbol> argsToSuperClass) throws SymbolNotDefinedException {
		// If user-defined constructor is not present so far, then
		// delete the system generated default constructor and add the user-defined 'constructor'
		if(!isConstructorDefined) {
			classMembers.removeSymbol(Constants.CONSTRUCTOR/*constructor.getName()*/);
			
			// Finally mark that user has defined the constructor
			isConstructorDefined = true;
		}
		
		// Environment.currentClass might get changed in below for loop's lookupFunction
		// hence take the backup
		ClassType currentClass = Environment.currentClass;
		
		// If this is a derived class and we still couldn't find any matching parent class's constructor, then throw the error
		if(!isBaseClass() && !isParentConstructorPresent(argsToSuperClass,true)) {
			throw new SymbolNotDefinedException("Super class constructor that matches " + className + constructor.getSignature() + " is not defined.");
		}
		
		
		Environment.currentClass = currentClass;
		// add the user-defined constructor
		classMembers.addSymbol(constructor);
	}
	
	/**
	 * This method checks if there is a user default constructor in at least one ancestor of
	 * current class.
	 * @param argsToSuperClass
	 * @param isOriginatingClass - 'true' if called from addConstructor() method, else 'false'. This is to differentiate 
	 * whether to check for no arguments constructor or not.
	 * @return
	 * @throws SymbolNotDefinedException
	 */
	private boolean isParentConstructorPresent(ArrayList<Symbol> argsToSuperClass, boolean isOriginatingClass) throws SymbolNotDefinedException {
		FunctionType parentConstructor = null;
		
		// Since base class doesn't have any ancestors, check for the constructor here itself
		if(isBaseClass()) {
			try {
				return ((OverloadedFunctionType) classMembers.getSymbol(Constants.CONSTRUCTOR)).getFunction(argsToSuperClass) != null;
			} catch(SymbolNotDefinedException e) {
				if(!isOriginatingClass) 
					// If constructor with parameters is not present, then check if no argument constructor is present or not.
					return ((OverloadedFunctionType) classMembers.getSymbol(Constants.CONSTRUCTOR)).getFunction(new ArrayList<Symbol>()) != null;
			}
		}
		
		for(int ancestorNo = 0; ancestorNo < ancestors.size();ancestorNo++) {

			try {
				parentConstructor = ((OverloadedFunctionType) classMembers.getSymbol(Constants.CONSTRUCTOR)).getFunction(argsToSuperClass);
			} catch (SymbolNotDefinedException e) {
				if(!isOriginatingClass) 
					try {
						// If constructor with parameters is not present, then check if no argument constructor is present or not.
						parentConstructor = ((OverloadedFunctionType) classMembers.getSymbol(Constants.CONSTRUCTOR)).getFunction(new ArrayList<Symbol>());
					} catch (SymbolNotDefinedException f) {}
			}
			
			// If constructor present in this ancestor, then return true
			if(parentConstructor != null)
				return true;
			// else search in this ancestor's ancestor
			else if(ancestors.get(ancestorNo).isParentConstructorPresent(argsToSuperClass,false))
				return true;
			
		}
		return false;
	}
	
	/**
	 * This method returns the symbol Name based on symbolName that is being passed.
	 * If not present in this class, the method will check the symbol in all its ancestors.
	 * @param symbolName
	 * @return
	 * @throws SymbolNotDefinedException
	 */
	public Symbol getSymbol(String symbolName) throws SymbolNotDefinedException {
		Symbol symbol = null;
		boolean found = false;
		
		// If this object is used before initialization and if this not the class 
		// level access, then prompt error
		if(!isInitialized && !name.equals(className)) {
			throw new SymbolNotDefinedException("Usage of '" + toString() + "' without initialization");
		}

		while(!found) {
			// Check if symbol name is present in this class or not. 
			if(classMembers.isSymbolPresent(symbolName))
					return classMembers.getSymbol(symbolName);
			else {
				// Else search for the symbolName in ancestor classes.
				for(int ancestorNo = 0; ancestorNo < ancestors.size();ancestorNo++) {
					symbol = ancestors.get(ancestorNo).getSymbol(symbolName);
					// If found, then break the loop, else keep searching
					if(symbol != null) {
						found = true; 
						break; 
					}
				}
			}
			
			if(!found)
				throw new SymbolNotDefinedException(symbolName);
		}
		
		return symbol;
	}
	
	/**
	 * Looks for ancestorName and add it as this class's ancestor
	 * @param ancestorName
	 * @throws SymbolNotDefinedException
	 */
	public void addAncestor(ClassType ancestor) throws SymbolNotDefinedException {
		ancestors.add(ancestor);
		
		// If ancestors are being added, then inherit the default constructor from ancestors,
		// hence remove the default constructor that was added at the time of creation of this class
		if(!isConstructorDefined) {
			classMembers.removeSymbol(Constants.CONSTRUCTOR);
			
			// Finally mark that user has defined the constructor
			isConstructorDefined = true;
		}
	}
	
	/**
	 * Checks if this is a base class or a derived class
	 * @return
	 */
	public boolean isBaseClass() {
		return ancestors.size() == 0;
	}
	
	/**
	 * Specify if the class-type contains class definition or an instance of
	 * object of a particular class-type 
	 * @return
	 */
	public boolean isClass() {
		return className.equals(name);
	}

}

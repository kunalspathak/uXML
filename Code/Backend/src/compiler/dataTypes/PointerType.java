package compiler.dataTypes;

import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;


public class PointerType extends Symbol {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ptrValue stores all kind of value that a pointer can save including itself
	 */
	private Symbol value = null;
	
	private String referenceType = null;
	
	// Denotes if current pointee is mutable or not.
	private boolean isPointeeMutable = false;


	protected PointerType(String name, boolean mutable)  {
		super(name, Constants.POINTER, mutable);	
	}

	
	/**
	 * This method is called whenever there is an occurrence of "pointer-type". This method
	 * creates the respective reference-type that is type of value which would be pointed by, by the
	 * defined pointer-type.
	 *  
	 * @param referenceType
	 * @throws IllegalSymbolValueException
	 * @throws ImmutableSymbolException
	 * @throws TypeMismatchException
	 */
	public void setReferenceType(String referenceType) throws IllegalSymbolValueException, ImmutableSymbolException, TypeMismatchException {
		this.referenceType = referenceType;
		/*// Generate the variable depending on the refType
		try {
			
			// make it mutable, and once the reference is created, make it non-mutable.
			// This is needed since generate() method might set some initialization value
			this.value = SymbolMachine.generate(Constants.BLANK, referenceType, true);
			
			this.value.setMutableStatus(false);
			
		} catch (IllegalSymbolValueException e) {
			throw e;
		} catch (ImmutableSymbolException e) {
			throw e;
		} catch (TypeMismatchException e) {
			throw e;
		}*/
		
	}
	
	/**
	 * This method is used for setting the pointer to the pointee that is received 
	 * as an argument.
	 * @param pointee
	 * @throws ImmutableSymbolException 
	 */
	public void setPointee(Symbol pointee) {
		this.value = pointee;
	}
	
	/**
	 * Checks if the value to which this pointer points to is constant or not.
	 * 
	 * @return the isPointeeMutable
	 */
	public boolean isPointeeMutable() {
		return isPointeeMutable; //value == null? false:isPointeeMutable; 
	}

	/**
	 * Set the mutable status of value that is pointed by this pointer
	 * @param isPointeeMutable the isPointeeMutable to set
	 */
	public void setPointeeMutable(boolean isPointeeMutable) {
		this.isPointeeMutable = isPointeeMutable;
	}

	/**
	 * @return the referenceType
	 */
	public String getReferenceType() {
		return referenceType;
	}

	@Override
	public String getValue() {
		return toString();
	}

	/**
	 * @return the ptrValue
	 */
	public Symbol getPtrValue() {
		return value;
	}
	
	@Override
	public void setValue(Symbol ptrValue) throws IllegalSymbolValueException,
			TypeMismatchException, ImmutableSymbolException {
		
		// If this is constant pointer (ptrMutable) or 
		// If this is pointer to a constant variable (valueMutable) or 
		// If the ptrValue is not mutable, but the value to which this pointer points is mutable
		// then thrown the exception
		if(!isMutable() || (this.isPointeeMutable() && !ptrValue.isMutable()))
			throw new ImmutableSymbolException(name);
		
		
	/*	// The type-mismatch is checked only if this pointer represents a variable 
		// and not any constant value.
		if(!(ptrValue instanceof PointerType && ((PointerType)ptrValue).isPointerType())) {
			// Get the data-type of lhs and rhs
			String lhsType = value!=null && ((PointerType)value).isPointerType() ? ((PointerType)value).getPointerType() : getPointerType();
			String rhsType = ptrValue instanceof PointerType? ((PointerType)ptrValue).getPointerType() : ptrValue.getType();
			
			// If the data-type doesn't match, then throw exception
			if(!lhsType.equals(rhsType) && !lhsType.substring(1,lhsType.length() - 1).equals(rhsType))
				throw new TypeMismatchException(rhsType,lhsType);
		}*/
		
		/*boolean a = false;
		try {
			a = DataTypeFinder.getDataType(this).equals(DataTypeFinder.getDataType(ptrValue));
		} catch(SymbolNotDefinedException e ){
			System.err.println(e.getMessage());
		}*/
		
		if(getDataType() == ptrValue.getDataType()/*a*/
		) {
			if(ptrValue instanceof ConstantValue)
				this.value = ((ConstantValue)ptrValue).pointerValue();
			else 
			{
				PointerType pointerType = (PointerType) ptrValue;
				Symbol pointerValue = pointerType.getPtrValue();
				// If this is overloaded function list, then get the appropriate function
				if(pointerValue instanceof OverloadedFunctionType) {
					String required = null;
					FunctionType function = null;
					try {
						required = DataTypeFinder.getDataType(this.getReferenceType());
						// Get the appropriate function depending on the signature
						function = ((OverloadedFunctionType)pointerValue).getFunction(required);
						if(function == null)
							throw new SymbolNotDefinedException();
						else
							this.value = function;
					} catch (SymbolNotDefinedException e) {
						throw new TypeMismatchException("no appropriate signature for " + pointerValue.getName(), pointerValue.getName() + required);
					}
				}
				// else, simply set whatever value is there
				else
					this.value = ((PointerType)ptrValue).getPtrValue();
			}
		} else
			try {
				throw new IllegalSymbolValueException(DataTypeFinder.getDataType(this),DataTypeFinder.getDataType(ptrValue));
			} catch (SymbolNotDefinedException e) {}
		
		// Update the data-type. Specify to what data-type this pointer points to.
//		this.type = Constants.POINTER + " -> " + ptrValue;

	}
	


	@Override
	public String toString() {
		return printIt(true);
	}


	/**
	 * This method returns the data-structure to which this pointer points to.
	 * @return the pointerType
	 */
	public String getPointerType() {
		return printIt(false);
	}

	/**
	 * This method prints the pointer references that this pointer points to.
	 * It displays the inner most value that is stored in this pointer with the
	 * set of enclosing square brackets. Each pair of square bracket represents 
	 * 1 level of pointer.
	 * Eg.  
	 * 		int i; 	i = 5 	---> 5 
	 * 		int *j; j = &i  ---> [5]
	 * 		int *k; k = &j 	---> [[5]]
	 * 
	 * @param isValue - If true, prints the inner most value else prints the data-type of innermost value.
	 * @return
	 */
	private String printIt(boolean isValue) {
		if(value != null && value instanceof PointerType)
			return new String("{" + ((PointerType)value).printIt(isValue) + "}*");
		else
			return "{" + (value == null? "" : isValue?value.toString():value.getType()) + "}*";
	}

}

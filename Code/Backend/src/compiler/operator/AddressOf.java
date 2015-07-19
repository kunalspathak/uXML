package compiler.operator;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.dataTypes.ConstantValue;
import compiler.dataTypes.DataTypeFinder;
import compiler.dataTypes.PointerType;
import compiler.dataTypes.SymbolMachine;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.SymbolNotDefinedException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;



/**
 * Address of / reference of
 * @author kunal
 *
 */
public class AddressOf extends Operators {

	@Override
	public Symbol evaluate(Symbol rhs) throws IllegalSymbolValueException {
		PointerType rhsPointer = null;
		
		if(rhs instanceof ConstantValue)
			throw new IllegalSymbolValueException("Variable","Constant/Expression");
		// Returns the reference to this object. The object needs to be embedded inside a pointer type
		// Create the new pointer-type and set the value to 
		rhsPointer = (PointerType)SymbolMachine.generate(Constants.BLANK, Constants.POINTER, rhs.isMutable());
		rhsPointer.setPointee(rhs);
		try {
			rhsPointer.setReferenceType(DataTypeFinder.getDataType(rhs));
		} catch (ImmutableSymbolException e) {} 
		catch (TypeMismatchException e) {} 
		catch (SymbolNotDefinedException e) {}
		return rhsPointer;
	}

	@Override
	public Symbol evaluate(Symbol lhs, Symbol rhs)
			throws IllegalSymbolValueException {
		return null;
	}

}

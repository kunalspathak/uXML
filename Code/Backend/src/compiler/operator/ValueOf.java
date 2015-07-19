package compiler.operator;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.dataTypes.ConstantValue;
import compiler.dataTypes.PointerType;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.util.Constants;


/**
 * Value that is pointed by this pointer
 * @author kunal
 *
 */
public class ValueOf extends Operators {

	@Override
	public Symbol evaluate(Symbol rhs) throws IllegalSymbolValueException {
		Symbol value;
		
		if(!(rhs instanceof PointerType) && !(rhs instanceof ConstantValue))
			throw new IllegalSymbolValueException(Constants.POINTER, rhs.getType());
		
		if(rhs instanceof ConstantValue) 
			value = ((ConstantValue)rhs).pointerValue();
		else
			value = ((PointerType)rhs).getPtrValue();
		
		return value;
	}

	@Override
	public Symbol evaluate(Symbol lhs, Symbol rhs)
			throws IllegalSymbolValueException {
		return null;
	}

}

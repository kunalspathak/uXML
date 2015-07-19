package compiler.operator;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.dataTypes.ArrayType;
import compiler.dataTypes.ConstantValue;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.util.Constants;
import compiler.util.Constants.DATA_TYPE;


public class ArrayAccess extends Operators {

	@Override
	public Symbol evaluate(Symbol rhs) throws IllegalSymbolValueException {
		return null;
	}

	@Override
	public Symbol evaluate(Symbol lhs, Symbol rhs)
			throws IllegalSymbolValueException {
		
		// array shouldn't be null or it should be a constant value with
		// ARRAY as datatype
		if(lhs == null || !(lhs instanceof ArrayType || (lhs instanceof ConstantValue && lhs.getDataType() == DATA_TYPE.ARRAY))
				)
			throw new IllegalSymbolValueException(Constants.ARRAY+ " - '[]'");
		
		if(rhs == null || rhs.getDataType() != DATA_TYPE.INT)
			throw new IllegalSymbolValueException(Constants.INT + " - 'array index'");
		
		// If ArrayType then return the appropriate element
		if(lhs instanceof ArrayType) 
			return ((ArrayType) lhs).get(intValue(rhs));
		// else fetch the hashmp and return the appropriate element
		else if(lhs instanceof ConstantValue) {
			return ((ConstantValue) lhs).arrayValue().get(intValue(rhs));
		}
		
		return null;
	}

}

package compiler.operator;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.dataTypes.ConstantValue;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.util.Constants;
import compiler.util.Constants.DATA_TYPE;

public class BitRightShift extends Operators {

	private Symbol result = null;
	
	@Override
	public Symbol evaluate(Symbol rhs) throws IllegalSymbolValueException {
		return null;
	}

	@Override
	public Symbol evaluate(Symbol lhs, Symbol rhs)
			throws IllegalSymbolValueException {
		try {
			
			if(lhs == null || rhs == null)
				throw new IllegalSymbolValueException(Constants.INT + " and/or " + Constants.FLOAT + " - '>>'");

			// int = int & int
			if (lhs.getDataType() == DATA_TYPE.INT && rhs.getDataType() == DATA_TYPE.INT) 
				result = new ConstantValue(Constants.INT, String.valueOf(intValue(lhs) >> intValue(rhs)));
			
			else
				throw new IllegalSymbolValueException(Constants.INT + " - '>>'");

		} catch (IllegalSymbolValueException e) {
			throw e;
		}
		return result;
	}

}

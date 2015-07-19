package compiler.operator;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.dataTypes.ConstantValue;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.util.Constants;
import compiler.util.Constants.DATA_TYPE;


/**
 * Subtraction
 * 
 * @author kunal
 * 
 */
public class Sub extends Operators {

	private Symbol result = null;

	@Override
	public Symbol evaluate(Symbol rhs) throws IllegalSymbolValueException {
		try {
			if(rhs == null)
				throw new IllegalSymbolValueException(Constants.INT + " - '-'");

			// int = - int
			if (rhs.getDataType() == DATA_TYPE.INT) 
				result = new ConstantValue(Constants.INT, String.valueOf(-1 * intValue(rhs)));
			
			// FLOAT = - FLOAT
			else if (rhs.getDataType() == DATA_TYPE.FLOAT) 
				result = new ConstantValue(Constants.FLOAT, String.valueOf(-1 * floatValue(rhs)));
			
			else
				throw new IllegalSymbolValueException(Constants.INT + " - '-'");
		} catch (IllegalSymbolValueException e) {
			throw e;
		}
		return result;
	}

	@Override
	public Symbol evaluate(Symbol lhs, Symbol rhs) throws IllegalSymbolValueException {
		try {
			
			if(lhs == null || rhs == null)
				throw new IllegalSymbolValueException(Constants.INT + " and/or " + Constants.FLOAT);

			// int = int - int
			if (lhs.getDataType() == DATA_TYPE.INT && rhs.getDataType() == DATA_TYPE.INT) 
				result = new ConstantValue(Constants.INT, String.valueOf(intValue(lhs) - intValue(rhs)));
			
			// FLOAT = FLOAT - FLOAT
			else if (lhs.getDataType() == DATA_TYPE.FLOAT && rhs.getDataType() == DATA_TYPE.FLOAT) 
				result = new ConstantValue(Constants.FLOAT, String.valueOf(floatValue(lhs) - floatValue(rhs)));
			
			// FLOAT = int - FLOAT
			else if (lhs.getDataType() == DATA_TYPE.INT && rhs.getDataType() == DATA_TYPE.FLOAT) 
				result = new ConstantValue(Constants.FLOAT, String.valueOf(intValue(lhs) - floatValue(rhs)));

			// FLOAT = FLOAT - int
			else if (lhs.getDataType() == DATA_TYPE.FLOAT && rhs.getDataType() == DATA_TYPE.INT) 
				result = new ConstantValue(Constants.FLOAT, String.valueOf(floatValue(lhs) - intValue(rhs)));

			else
				throw new IllegalSymbolValueException(Constants.INT + " and/or " + Constants.FLOAT);

		} catch (IllegalSymbolValueException e) {
			throw e;
		}
		return result;
	}

}

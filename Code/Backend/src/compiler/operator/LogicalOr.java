package compiler.operator;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.dataTypes.ConstantValue;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.util.Constants;
import compiler.util.Constants.DATA_TYPE;


/**
 * Logical OR
 * 
 * @author kunal
 * 
 */
public class LogicalOr extends Operators {

	private Symbol result = null;

	@Override
	public Symbol evaluate(Symbol rhs) {
		return null;
	}

	@Override
	public Symbol evaluate(Symbol lhs, Symbol rhs) throws IllegalSymbolValueException {
		try {
			
			if(lhs == null || rhs == null)
				throw new IllegalSymbolValueException(Constants.BOOLEAN + " - '||'");

			// bool = bool || bool
			if (lhs.getDataType() == DATA_TYPE.BOOL && rhs.getDataType() == DATA_TYPE.BOOL) 
				result = new ConstantValue(Constants.BOOLEAN, (booleanValue(lhs) || booleanValue(rhs))?"true":"false");
			
			else
				throw new IllegalSymbolValueException(Constants.BOOLEAN + " - '||'");

		} catch (IllegalSymbolValueException e) {
			throw e;
		}
		return result;
	}
}

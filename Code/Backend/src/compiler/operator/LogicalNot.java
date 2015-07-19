package compiler.operator;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.dataTypes.ConstantValue;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.util.Constants;
import compiler.util.Constants.DATA_TYPE;


/**
 * Logical NOT
 * 
 * @author kunal
 * 
 */
public class LogicalNot extends Operators {

	private Symbol result = null;

	@Override
	public Symbol evaluate(Symbol rhs) throws IllegalSymbolValueException  {
		
		if(rhs == null)
			throw new IllegalSymbolValueException(Constants.BOOLEAN + " - '!'");

		if (rhs.getDataType() == DATA_TYPE.BOOL) 
			result = new ConstantValue(Constants.BOOLEAN, !booleanValue(rhs)?"true":"false");
		
		else
			throw new IllegalSymbolValueException(Constants.BOOLEAN + " - '!'");
		return result;
	}

	@Override
	public Symbol evaluate(Symbol lhs, Symbol rhs){return null;}
}

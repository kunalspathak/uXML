package compiler.operator;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.dataTypes.ConstantValue;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.util.Constants;
import compiler.util.Constants.DATA_TYPE;


/**
 * Bit-wise COMPLEMENT
 * 
 * @author kunal
 * 
 */
public class BitComplement extends Operators {

	private Symbol result = null;

	@Override
	public Symbol evaluate(Symbol rhs) throws IllegalSymbolValueException  {
		
		if(rhs == null)
			throw new IllegalSymbolValueException(Constants.INT + " and/or " + Constants.FLOAT + " - '~'");

		if (rhs.getDataType() == DATA_TYPE.INT) 
			result = new ConstantValue(Constants.INT, String.valueOf(~intValue(rhs)));
		
		else
			throw new IllegalSymbolValueException(Constants.INT + " - '~'");
		return result;
	}

	@Override
	public Symbol evaluate(Symbol lhs, Symbol rhs){return null;}
}

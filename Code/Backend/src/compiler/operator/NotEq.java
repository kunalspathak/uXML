package compiler.operator;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.dataTypes.ConstantValue;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.util.Constants;
import compiler.util.Constants.DATA_TYPE;


/**
 * Not Equal
 * 
 * @author kunal
 * 
 */
public class NotEq extends Operators {

	private Symbol result = null;

	@Override
	public Symbol evaluate(Symbol rhs) {
		return null;
	}

	@Override
	public Symbol evaluate(Symbol lhs, Symbol rhs) throws IllegalSymbolValueException {
		try {
			
			if(lhs == null || rhs == null)
				throw new IllegalSymbolValueException(Constants.INT + " and/or " + Constants.FLOAT +  " and/or " + Constants.CHAR + " and/or " + Constants.BOOLEAN + " - '!='");

			// boolean = int != int
			if (lhs.getDataType() == DATA_TYPE.INT && rhs.getDataType() == DATA_TYPE.INT) 
				result = new ConstantValue(Constants.BOOLEAN, intValue(lhs) != intValue(rhs)?"true":"false");
			
			// boolean = FLOAT != FLOAT
			else if (lhs.getDataType() == DATA_TYPE.FLOAT && rhs.getDataType() == DATA_TYPE.FLOAT) 
				result = new ConstantValue(Constants.BOOLEAN, floatValue(lhs) != floatValue(rhs)?"true":"false");

			// boolean = int != FLOAT
			else if (lhs.getDataType() == DATA_TYPE.INT && rhs.getDataType() == DATA_TYPE.FLOAT) 
				result = new ConstantValue(Constants.BOOLEAN, intValue(lhs) != floatValue(rhs)?"true":"false");

			// boolean = FLOAT != int
			else if (lhs.getDataType() == DATA_TYPE.FLOAT && rhs.getDataType() == DATA_TYPE.INT) 
				result = new ConstantValue(Constants.BOOLEAN, floatValue(lhs) != intValue(rhs)?"true":"false");
			
			// boolean = char != char
			else if (lhs.getDataType() == DATA_TYPE.CHAR && rhs.getDataType() == DATA_TYPE.CHAR) 
				result = new ConstantValue(Constants.BOOLEAN, charValue(lhs) != charValue(rhs)?"true":"false");

			// boolean = boolean != boolean
			else if (lhs.getDataType() == DATA_TYPE.BOOL && rhs.getDataType() == DATA_TYPE.BOOL) 
				result = new ConstantValue(Constants.BOOLEAN, booleanValue(lhs) != booleanValue(rhs)?"true":"false");

			else
				throw new IllegalSymbolValueException(Constants.INT + " and/or " + Constants.FLOAT +  " and/or " + Constants.CHAR + " and/or " + Constants.BOOLEAN + " - '!='");

		} catch (IllegalSymbolValueException e) {
			throw e;
		}
		return result;
	}
}

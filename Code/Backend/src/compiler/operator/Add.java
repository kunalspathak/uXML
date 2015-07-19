package compiler.operator;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.dataTypes.ConstantValue;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.util.Constants;
import compiler.util.Constants.DATA_TYPE;


/**
 * Addition
 * @author kunal
 *
 */
public class Add extends Operators {

	private Symbol result = null;

	@Override
	public Symbol evaluate(Symbol rhs) {
		return null;
	}

	@Override
	public Symbol evaluate(Symbol lhs, Symbol rhs) throws IllegalSymbolValueException {
		try {
			if(lhs == null || rhs == null)
				throw new IllegalSymbolValueException(Constants.INT + " and/or " + Constants.FLOAT + " and/or " + Constants.STRING + " - '+'");

			// int = int + int
			if (lhs.getDataType() == DATA_TYPE.INT && rhs.getDataType() == DATA_TYPE.INT) 
				result = new ConstantValue(Constants.INT, String.valueOf(intValue(lhs) + intValue(rhs)));
			
			// FLOAT = FLOAT + FLOAT
			else if (lhs.getDataType() == DATA_TYPE.FLOAT && rhs.getDataType() == DATA_TYPE.FLOAT) 
				result = new ConstantValue(Constants.FLOAT, String.valueOf(floatValue(lhs) + floatValue(rhs)));
			
			// string = string + string
			else if (lhs.getDataType() == DATA_TYPE.STRING && rhs.getDataType() == DATA_TYPE.STRING) 
				result = new ConstantValue(Constants.STRING, stringValue(lhs) + stringValue(rhs));
			
			// FLOAT = int + FLOAT 
			else if (lhs.getDataType() == DATA_TYPE.INT && rhs.getDataType() == DATA_TYPE.FLOAT) 
				result = new ConstantValue(Constants.FLOAT, String.valueOf(intValue(lhs) + floatValue(rhs)));
			
			// FLOAT = FLOAT + int
			else if (lhs.getDataType() == DATA_TYPE.FLOAT && rhs.getDataType() == DATA_TYPE.INT) 
				result = new ConstantValue(Constants.FLOAT, String.valueOf(floatValue(lhs) + intValue(rhs)));
			
			// string = int + string
			else if (lhs.getDataType() == DATA_TYPE.INT && rhs.getDataType() == DATA_TYPE.STRING) 
				result = new ConstantValue(Constants.STRING, intValue(lhs) + stringValue(rhs));
				
			// string = string + int
			else if (lhs.getDataType() == DATA_TYPE.STRING && rhs.getDataType() == DATA_TYPE.INT) 
				result = new ConstantValue(Constants.STRING, stringValue(lhs) + intValue(rhs));
			
			// string = FLOAT + string 
			else if (lhs.getDataType() == DATA_TYPE.FLOAT && rhs.getDataType() == DATA_TYPE.STRING) 
				result = new ConstantValue(Constants.STRING, floatValue(lhs) + stringValue(rhs));

			// string = string + FLOAT
			else if (lhs.getDataType() == DATA_TYPE.STRING && rhs.getDataType() == DATA_TYPE.FLOAT) 
				result = new ConstantValue(Constants.STRING, stringValue(lhs) + floatValue(rhs));
			
			else
				throw new IllegalSymbolValueException(Constants.INT + " and/or " + Constants.FLOAT + " and/or " + Constants.STRING + " - '+'");
			
		} catch (IllegalSymbolValueException e) {
			throw e;
		}
		return result;
		
	}
}

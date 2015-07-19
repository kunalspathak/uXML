package compiler.dataTypes;

import java.util.HashMap;

import org.w3c.dom.Node;

import compiler.components.Environment;
import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Constants;


public class ConstantValue extends Symbol {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int intTypeValue;
	private float decimalTypeValue;
	private String stringTypeValue;
	private boolean boolTypeValue;
	private char charTypeValue;
	private String noTypeValue;
	private Node functionValue;
	private HashMap<Integer, Symbol> arrayValue;
	private Symbol pointerValue;
	private ClassType objectValue;
	private OverloadedFunctionType overloadedFunction;

	// TODO: represents the dynamic variables (for languages where declaration
	// is optional)
	private String userDefinedValue;

	public ConstantValue(String type, boolean mutable) {
		super(Constants.BLANK, type, mutable);
	}

	/**
	 * Only used when variable declaration is not mandatory. In that case,
	 * constant value could have name to refer.
	 * 
	 * @param name
	 *            - Name of the variable that is not defined.
	 * @param type
	 *            - Type of undefined variable.
	 * @param mutable
	 *            - mutable status.
	 */
	public ConstantValue(String name, String type, boolean mutable) {
		super(name, type, mutable);
	}

	/**
	 * If constant value is generated by this constructor, it will be always
	 * non-mutable
	 * 
	 * @param type
	 * @param value
	 * @throws IllegalSymbolValueException
	 */
	public ConstantValue(String type, String value)
			throws IllegalSymbolValueException {
		// Make it non-mutable
		super(Constants.BLANK, type, false);

		switch (getDataType()) {
		case INT:
			this.intTypeValue = IntegerType.parseValue(value);
			break;
		case FLOAT:
			this.decimalTypeValue = DecimalType.parseValue(value);
			break;
		case STRING:
			this.stringTypeValue = StringType.parseValue(value);
			break;
		case BOOL:
			this.boolTypeValue = BooleanType.parseValue(value);
			break;
		case CHAR:
			this.charTypeValue = CharType.parseValue(value);
			break;
		case DYNAMIC:
			this.noTypeValue = value;
			break;
		default:
			this.userDefinedValue = value;

		}
	}

	@Override
	public String toString() {
		switch (getDataType()) {
		case INT:
			return String.valueOf(intValue());
		case FLOAT:
			return String.valueOf(decimalValue());
		case STRING:
			return stringValue();
		case BOOL:
			return String.valueOf(booleanValue());
		case CHAR:
			return String.valueOf(charValue());
		case FUNCTION:
			return getType();
		case POINTER:
			return pointerValue().toString();
		case ARRAY: {
			StringBuffer arrayDesc = new StringBuffer("[");
			HashMap<Integer,Symbol> array = arrayValue();
			int count = 0;
			for(Integer index : array.keySet()) {
				if(count++ > 0)
					arrayDesc.append(",");
				arrayDesc.append(array.get(index));
			}
			arrayDesc.append("]");
			return arrayDesc.toString();
		}
			
		case CLASS:
			return objectValue().toString();
		case USERDEFINED:
			return userDefinedValue();

		default:
			return noTypeValue();
		}
	}

	@Override
	public String getValue() {
		return toString();
	}

	public int intValue() {
		return intTypeValue;
	}

	public float decimalValue() {
		return decimalTypeValue;
	}

	public String stringValue() {
		return stringTypeValue;
	}

	public boolean booleanValue() {
		return boolTypeValue;
	}

	public char charValue() {
		return charTypeValue;
	}

	public String noTypeValue() {
		return noTypeValue;
	}

	public Node functionValue() {
		return functionValue;
	}

	public ClassType objectValue() {
		return objectValue;
	}

	public HashMap<Integer, Symbol> arrayValue() {
		return arrayValue;
	}

	public Symbol pointerValue() {
		return pointerValue;
	}

	public OverloadedFunctionType overloadedFunctionValue() {
		return overloadedFunction;
	}

	public String userDefinedValue() {
		return userDefinedValue;
	}

	@Override
	public void setValue(Symbol symbol) throws IllegalSymbolValueException,
			TypeMismatchException {
		if (symbol == null || symbol.getDataType() == null)
			throw new IllegalSymbolValueException(this.type, null);

		// If variable declaration is not mandatory
		// /* and If this is a 'NO-TYPE' data-type */ then set
		// the data-type of constant to that of symbol. This leads to flexible
		// data-type.

		if (!Environment.isVarMandatory) {
			datatype = symbol.getDataType();
			type = symbol.getType();
			/*
			 * try { type = DataTypeFinder.getDataType(symbol); }
			 * catch(SymbolNotDefinedException e) { type = symbol.getType(); }
			 */
		}

		// If the 2 data-type doesn't match then throw TypeMismatchException
		if (this.datatype != symbol.getDataType())
			throw new TypeMismatchException(symbol.getType(), this.type);

		// Set the value according to the data-type
		switch (getDataType()) {
		case INT:
			this.intTypeValue = SymbolMachine.intValue(symbol);
			break;
		case FLOAT:
			this.decimalTypeValue = SymbolMachine.decimalValue(symbol);
			break;
		case STRING:
			this.stringTypeValue = SymbolMachine.stringValue(symbol);
			break;
		case BOOL:
			this.boolTypeValue = SymbolMachine.booleanValue(symbol);
			break;
		case CHAR:
			this.charTypeValue = SymbolMachine.charValue(symbol);
			break;
		case DYNAMIC:
			this.noTypeValue = symbol.getValue();
			break;
		case FUNCTION:
			this.functionValue = SymbolMachine.functionValue(symbol);
			break;
		case ARRAY:
			this.arrayValue = SymbolMachine.arrayValue(symbol);
			break;
		case POINTER:
			this.pointerValue = SymbolMachine.pointerValue(symbol);
			break;
		case CLASS:
			this.objectValue = SymbolMachine.objectValue(symbol);
			break;
		case OVERLOADED_FUNCTIONS:
			this.overloadedFunction = SymbolMachine
					.overloadedFunctionValue(symbol);
			break;

		default:
			// this.userDefinedValue = SymbolMachine.userDefinedValue(symbol);
			throw new IllegalSymbolValueException(
					"Unknown data type of a symbol.");

		}
	}

	/*
	 * @Override public Object value() { switch(getDataType()) { case INT :
	 * return intValue(); case FLOAT : return decimalValue(); case STRING :
	 * return stringValue(); case BOOL : return booleanValue(); case CHAR :
	 * return charValue(); case USERDEFINED : return userDefinedValue();
	 * default: return noTypeValue(); } }
	 */

}

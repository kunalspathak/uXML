package compiler.operator;

import java.util.List;

import compiler.components.Operators;
import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.OperatorNotFoundException;


public class Operator {
	
	/**
	 * This method takes the operator name and arguments and return the result of evaluation 
	 * wrapped in Symbol.
	 * @param operatorName - Operator name
	 * @param args - arguments
	 * @return - Result of evaluation
	 * @throws OperatorNotFoundException
	 * @throws IllegalSymbolValueException
	 */
	public static Symbol execute(String operatorName, List<Symbol> args) throws OperatorNotFoundException, IllegalSymbolValueException {
		Operators operator = null;
		if("+".equals(operatorName)) 	  operator = new Add();
		else if("-".equals(operatorName)) operator = new Sub();
		else if("*".equals(operatorName)) operator = new Mul();
		else if("/".equals(operatorName)) operator = new Div();
		else if("%".equals(operatorName)) operator = new Mod();
		else if("[]".equals(operatorName)) operator = new ArrayAccess();
		
		// Conditional
		else if("==".equals(operatorName)) operator = new Eq();
		else if("!=".equals(operatorName)) operator = new NotEq();
		else if(")".equals(operatorName)) operator = new Gt();
		else if(")=".equals(operatorName)) operator = new GtEq();
		else if("(".equals(operatorName)) operator = new Lt();
		else if("(=".equals(operatorName)) operator = new LtEq();
		
		// Bit-wise
		else if("$".equals(operatorName)) operator = new BitAnd();
		else if("|".equals(operatorName)) operator = new BitOr();
		else if("^".equals(operatorName)) operator = new BitXor();
		else if("~".equals(operatorName)) operator = new BitComplement();
		else if("((".equals(operatorName)) operator = new BitLeftShift();
		else if("))".equals(operatorName)) operator = new BitRightShift();
		
		// Logical
		else if("$$".equals(operatorName)) operator = new LogicalAnd();
		else if("||".equals(operatorName)) operator = new LogicalOr();
		else if("!".equals(operatorName)) operator = new LogicalNot();
		
		// Pointer
		else if("@".equals(operatorName)) operator = new AddressOf();
		else if("#".equals(operatorName)) operator = new ValueOf();
	
		else
			throw new OperatorNotFoundException(operatorName);
		
		if(args.size() == 1)
			return operator.evaluate(args.get(0));
		else
			return operator.evaluate(args.get(0), args.get(1));
	}
	
	
}

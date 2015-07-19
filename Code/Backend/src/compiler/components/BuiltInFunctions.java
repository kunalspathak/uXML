package compiler.components;

import compiler.util.Constants;
import compiler.util.Constants.FUNCTION_TYPE;

/**
 * The class is used to check if the function name encountered is user-defined function
 * or a built-in function. For functional languages, everything including operators, assignment,
 * if condition are functions. The class used to distinguish these functions from normal
 * user defined functions.
 * @author kunal
 *
 */
public class BuiltInFunctions {
	
	/**
	 * The method returns the type of function
	 * @param functionName - Name of the function to be checked
	 * @return - OPERATOR or ASSIGNMENT or IF or USER_DEFINED
	 */
	public static FUNCTION_TYPE getFunctionType(String functionName) {
		
		// If function name is blank, then it is a lambda function
		if(functionName.trim().length() == 0)
			return FUNCTION_TYPE.USER_DEFINED;
		
		// Change the 'map' or 'reduce' keyword into actual operators
		if(Constants.MAP.equals(functionName))
			functionName = Constants.MAP_FUNCTION;
		else if(Constants.REDUCE.equals(functionName))
			functionName = Constants.REDUCE_FUNCTION;

		// If assignment
		if (Constants.ASSIGNMENT_OPERATOR.equals(functionName))
			return FUNCTION_TYPE.ASSIGNMENT;

		// If arithmetic or conditional
		else if(Constants.ARITHMETIC_OPERATORS.contains(functionName) || Constants.CONDITIONAL_OPERATORS.contains(functionName)
				|| Constants.FUNCTIONAL_OPERATORS.contains(functionName) || Constants.LOGICAL_NOT.contains(functionName))
			return FUNCTION_TYPE.OPERATOR;
		
		
		/*// If 'if' statement
		else if (Constants.IF_OPERATOR.equals(functionName))
			return FUNCTION_TYPE.IF;*/
		
		// user-defined function
		return FUNCTION_TYPE.USER_DEFINED;
	}

}

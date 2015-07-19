package compiler.util;

public class Constants {

	final public static String INT = "int";
	final public static String FLOAT = "float";
	final public static String STRING = "string";
	final public static String BOOLEAN = "boolean";
	final public static String CHAR = "char";
	final public static String NO_TYPE = "Dynamic";
	final public static String FUNCTION = "function";
	final public static String OVERLOADED_FUNCTIONS = "overloadedFunctions";
	final public static String POINTER = "pointer";
	final public static String ARRAY = "array";
	final public static String CLASS = "class";
	final public static String CONSTRUCTOR = "#new#";
	final public static String SUPER_CLASS = "super";
	final public static String THIS = "this";
	final public static String VOID = "void";

	final public static String YES = "yes";
	final public static String NO = "no";

	final public static String CONDITIONAL_OPERATORS = "== != ) )= ( (= $$ ||";
	final public static String LOGICAL_NOT = "!";
	final public static String FUNCTIONAL_OPERATORS = "(=) )=(";
	final public static String ARITHMETIC_OPERATORS = "+ - * / % $ | ^ ~ )) (( @ # []";
	final public static String ASSIGNMENT_OPERATOR = "=";
	final public static String IF_OPERATOR = "if";

	final public static String MAP_FUNCTION = ")=(";
	final public static String MAP = "map";
	final public static String REDUCE_FUNCTION = "(=)";
	final public static String REDUCE = "reduce";

	public static enum DATA_TYPE {INT,FLOAT, STRING, BOOL, CHAR, DYNAMIC, USERDEFINED, FUNCTION, POINTER, ARRAY, OVERLOADED_FUNCTIONS, CLASS};
	public static enum FUNCTION_TYPE {OPERATOR, ASSIGNMENT, IF, USER_DEFINED};

	final public static String BLANK = "";
	final public static String LAMBDA = "#lambda#";

	// uXML specific
	final public static String UXML = "uXML";
	final public static String LANGUAGE = "language";
	final public static String SCOPE = "scope";
	final public static String STATIC = "static";
	final public static String DYNAMIC = "dynamic";
	final public static String VAR_DECLARATION = "var-declaration";
	final public static String NAME = "name";
	final public static String VALUE = "value";
	final public static String TYPE = "type";
	final public static String SIZE = "size";
	final public static String START_INDEX = "startIndex";
	final public static String MUTABLE = "mutable";
	final public static String PTR_MUTABLE = "ptrMutable";
	final public static String VALUE_MUTABLE = "valueMutable";
	final public static String REF_TYPE = "refType";
	final public static String ADDRESS_OF = "@";
	final public static String VALUE_OF = "#";
	final public static String VALUE_AT_INDEX= "[]";

	final public static String BLOCK = "block";
	final public static String CALL = "call";
	final public static String TRACE = "trace";
	final public static String CAST = "cast";
	final public static String RETURN = "return";
	final public static String EXPR = "expr";
	final public static String OPERATOR = "operator";
	final public static String ASSIGN = "assign";
	final public static String VARIABLE = "variable";
	final public static String CONSTANT = "constant";
	final public static String FOR_LOOP = "for-loop";
	final public static String FROM = "from";
	final public static String STEP = "step";
	final public static String ITERATE = "iterate";
	final public static String TILL = "till";
	final public static String CONSTRUCTOR_ELEMENT = "constructor";
	final public static String PRINT = "print";
	final public static String PRINTLN = "println";
	final public static String FUNCTION_TYPE = "function-type";
	final public static String RETURNS = "returns";
	final public static String MEMBER_VAR = "member-Var";
	final public static String AUTOMATIC_VAR = "automatic-Var";
	final public static String GLOBAL_VAR = "global-Var";
	final public static String VAR = "var";
	final public static String ARRAY_TYPE = "array-type";
	final public static String DIM = "dim";
	final public static String ENUM_TYPE = "enum-type";
	final public static String ENUM_CONSTANT = "enum-constant";
	final public static String MAP_TYPE = "map-type";
	final public static String POINTER_TYPE = "pointer-type";
	final public static String CLASS_TYPE = "class-type";
	final public static String INHERITS = "inherits";
	final public static String FIELD = "field";



}

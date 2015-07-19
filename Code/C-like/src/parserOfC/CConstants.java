/* Generated By:JavaCC: Do not edit this line. CConstants.java */
package parserOfC;


/** 
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
@SuppressWarnings("all")
public interface CConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int INT_CONSTANT = 5;
  /** RegularExpression Id. */
  int FLOAT_CONSTANT = 6;
  /** RegularExpression Id. */
  int STRING_CONSTANT = 7;
  /** RegularExpression Id. */
  int BOOL_CONSTANT = 8;
  /** RegularExpression Id. */
  int CHAR_CONSTANT = 9;
  /** RegularExpression Id. */
  int PLUS = 16;
  /** RegularExpression Id. */
  int MINUS = 17;
  /** RegularExpression Id. */
  int ASTERISK = 18;
  /** RegularExpression Id. */
  int DIVIDE = 19;
  /** RegularExpression Id. */
  int MOD = 20;
  /** RegularExpression Id. */
  int AMPERSAND = 21;
  /** RegularExpression Id. */
  int BIT_OR = 22;
  /** RegularExpression Id. */
  int BIT_XOR = 23;
  /** RegularExpression Id. */
  int BIT_COMPLEMENT = 24;
  /** RegularExpression Id. */
  int BIT_LEFT_SHIFT = 25;
  /** RegularExpression Id. */
  int BIT_RIGHT_SHIFT = 26;
  /** RegularExpression Id. */
  int INCREMENT = 27;
  /** RegularExpression Id. */
  int DECREMENT = 28;
  /** RegularExpression Id. */
  int AND = 29;
  /** RegularExpression Id. */
  int OR = 30;
  /** RegularExpression Id. */
  int NOT = 31;
  /** RegularExpression Id. */
  int EQ = 32;
  /** RegularExpression Id. */
  int NT_EQ = 33;
  /** RegularExpression Id. */
  int LT = 34;
  /** RegularExpression Id. */
  int LT_EQ = 35;
  /** RegularExpression Id. */
  int GT_EQ = 36;
  /** RegularExpression Id. */
  int GT = 37;
  /** RegularExpression Id. */
  int IF = 38;
  /** RegularExpression Id. */
  int ELSE = 39;
  /** RegularExpression Id. */
  int ELSEIF = 40;
  /** RegularExpression Id. */
  int DO = 41;
  /** RegularExpression Id. */
  int WHILE = 42;
  /** RegularExpression Id. */
  int FOR = 43;
  /** RegularExpression Id. */
  int CALL = 44;
  /** RegularExpression Id. */
  int PRINT = 45;
  /** RegularExpression Id. */
  int PRINTLN = 46;
  /** RegularExpression Id. */
  int CONST = 47;
  /** RegularExpression Id. */
  int FUNCTION = 48;
  /** RegularExpression Id. */
  int RETURN = 49;
  /** RegularExpression Id. */
  int BLOCK_START = 50;
  /** RegularExpression Id. */
  int BLOCK_END = 51;
  /** RegularExpression Id. */
  int SQUARE_START = 52;
  /** RegularExpression Id. */
  int SQUARE_END = 53;
  /** RegularExpression Id. */
  int ROUND_START = 54;
  /** RegularExpression Id. */
  int ROUND_END = 55;
  /** RegularExpression Id. */
  int ASSIGN = 56;
  /** RegularExpression Id. */
  int COMMA = 57;
  /** RegularExpression Id. */
  int STMT_TERMINATOR = 58;
  /** RegularExpression Id. */
  int INT = 59;
  /** RegularExpression Id. */
  int FLOAT = 60;
  /** RegularExpression Id. */
  int CHAR = 61;
  /** RegularExpression Id. */
  int BOOL = 62;
  /** RegularExpression Id. */
  int STRUCT = 63;
  /** RegularExpression Id. */
  int VOID = 64;
  /** RegularExpression Id. */
  int FUNC_PTR = 65;
  /** RegularExpression Id. */
  int ID = 66;
  /** RegularExpression Id. */
  int ALPHA = 67;
  /** RegularExpression Id. */
  int DIGIT = 68;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int InsideComment = 1;
  /** Lexical state. */
  int InsideNewComment = 2;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\r\"",
    "\"\\t\"",
    "\"\\n\"",
    "<INT_CONSTANT>",
    "<FLOAT_CONSTANT>",
    "<STRING_CONSTANT>",
    "<BOOL_CONSTANT>",
    "<CHAR_CONSTANT>",
    "\"/*\"",
    "\"*/\"",
    "<token of kind 12>",
    "\"//\"",
    "<token of kind 14>",
    "<token of kind 15>",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"%\"",
    "\"&\"",
    "\"|\"",
    "\"^\"",
    "\"~\"",
    "\"<<\"",
    "\">>\"",
    "\"++\"",
    "\"--\"",
    "\"&&\"",
    "\"||\"",
    "\"!\"",
    "\"==\"",
    "\"!=\"",
    "\"<\"",
    "\"<=\"",
    "\">=\"",
    "\">\"",
    "\"if\"",
    "\"else\"",
    "\"elseif\"",
    "\"do\"",
    "\"while\"",
    "\"for\"",
    "\"call\"",
    "\"print\"",
    "\"println\"",
    "\"const\"",
    "\"function\"",
    "\"return\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\"(\"",
    "\")\"",
    "\"=\"",
    "\",\"",
    "\";\"",
    "\"int\"",
    "\"float\"",
    "\"char\"",
    "\"boolean\"",
    "\"struct\"",
    "\"void\"",
    "\"funcptr\"",
    "<ID>",
    "<ALPHA>",
    "<DIGIT>",
  };

}

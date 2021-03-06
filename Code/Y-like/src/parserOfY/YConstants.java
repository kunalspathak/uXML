/* Generated By:JavaCC: Do not edit this line. YConstants.java */
package parserOfY;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
@SuppressWarnings("all")
public interface YConstants {

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
  int PLUS = 13;
  /** RegularExpression Id. */
  int MINUS = 14;
  /** RegularExpression Id. */
  int ASTERISK = 15;
  /** RegularExpression Id. */
  int DIVIDE = 16;
  /** RegularExpression Id. */
  int MOD = 17;
  /** RegularExpression Id. */
  int AMPERSAND = 18;
  /** RegularExpression Id. */
  int BIT_OR = 19;
  /** RegularExpression Id. */
  int BIT_XOR = 20;
  /** RegularExpression Id. */
  int BIT_COMPLEMENT = 21;
  /** RegularExpression Id. */
  int AND = 22;
  /** RegularExpression Id. */
  int OR = 23;
  /** RegularExpression Id. */
  int NOT = 24;
  /** RegularExpression Id. */
  int EQ = 25;
  /** RegularExpression Id. */
  int NT_EQ = 26;
  /** RegularExpression Id. */
  int LT = 27;
  /** RegularExpression Id. */
  int LT_EQ = 28;
  /** RegularExpression Id. */
  int GT_EQ = 29;
  /** RegularExpression Id. */
  int GT = 30;
  /** RegularExpression Id. */
  int MAP = 31;
  /** RegularExpression Id. */
  int REDUCE = 32;
  /** RegularExpression Id. */
  int LAMBDA = 33;
  /** RegularExpression Id. */
  int PROGRAM = 34;
  /** RegularExpression Id. */
  int IF = 35;
  /** RegularExpression Id. */
  int ELSE = 36;
  /** RegularExpression Id. */
  int WHILE = 37;
  /** RegularExpression Id. */
  int FOR = 38;
  /** RegularExpression Id. */
  int TO = 39;
  /** RegularExpression Id. */
  int DOWNTO = 40;
  /** RegularExpression Id. */
  int CALL = 41;
  /** RegularExpression Id. */
  int WRITE = 42;
  /** RegularExpression Id. */
  int WRITELN = 43;
  /** RegularExpression Id. */
  int DEF = 44;
  /** RegularExpression Id. */
  int RETURN = 45;
  /** RegularExpression Id. */
  int BLOCK_START = 46;
  /** RegularExpression Id. */
  int BLOCK_END = 47;
  /** RegularExpression Id. */
  int SQUARE_START = 48;
  /** RegularExpression Id. */
  int SQUARE_END = 49;
  /** RegularExpression Id. */
  int ROUND_START = 50;
  /** RegularExpression Id. */
  int ROUND_END = 51;
  /** RegularExpression Id. */
  int ASSIGN = 52;
  /** RegularExpression Id. */
  int COMMA = 53;
  /** RegularExpression Id. */
  int COLON = 54;
  /** RegularExpression Id. */
  int VOID = 55;
  /** RegularExpression Id. */
  int ID = 56;
  /** RegularExpression Id. */
  int ALPHA = 57;
  /** RegularExpression Id. */
  int DIGIT = 58;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int InsideNewComment = 1;

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
    "\"#\"",
    "<token of kind 11>",
    "<token of kind 12>",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"%\"",
    "\"&\"",
    "\"|\"",
    "\"^\"",
    "\"~\"",
    "\"and\"",
    "\"or\"",
    "\"not\"",
    "\"==\"",
    "\"!=\"",
    "\"<\"",
    "\"<=\"",
    "\">=\"",
    "\">\"",
    "\"map\"",
    "\"reduce\"",
    "\"lambda\"",
    "\"PROGRAM\"",
    "\"if\"",
    "\"else\"",
    "\"while\"",
    "\"for\"",
    "\"to\"",
    "\"downto\"",
    "\"call\"",
    "\"print\"",
    "\"println\"",
    "\"def\"",
    "\"return\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\"(\"",
    "\")\"",
    "\"=\"",
    "\",\"",
    "\":\"",
    "\"void\"",
    "<ID>",
    "<ALPHA>",
    "<DIGIT>",
  };

}

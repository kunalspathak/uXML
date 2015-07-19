// created by xv 1.0.2 (c) 2009 axel.schreiner@rit.edu
package xvHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Collection;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import compiler.util.Constants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import java.io.IOException;



public class CodeAnalysis {
	
	// Prints information based on value of varDetailsToPrint
	// 1 : name
	// 2 : type
	private int varDetailsToPrint = 1;
	private int mcc = 1; 
	private String lined = "____________________________________________";
	private String dashed = "--------------------------------------------";
	private int callCount = 0;
	private boolean isFunctionCall = true;
	private int nestedForCount = 0;
	private int nestedIterationCount = 0;
	// Helstead Metrics related variables
	private int N1, n1, N2, n2 = 0;
	private int loc,N,n = 0;
	double V,D, L, E, T, B, MI, MCC = 0;
	HashMap<String,Integer> overridenFunction = new HashMap<String, Integer>();
	// Operator collection for Helstead Metric 
	HashMap<String,Integer> hmOperator = new HashMap<String, Integer>();
	// Operand collection for Helstead Metric
	HashMap<String,Integer> hmOperand = new HashMap<String, Integer>();
	
	protected final 
	    javax.xml.xpath.XPathExpression
      children, // 47
      childrenCount, // 48
      functions, // 51
      functionParams, // 52
      functionReturnType, // 53
      functionCalls, // 54
      variablesDef, // 55
      constantsUsed, // 56
      mccCount_1, // 57
      mccCount_2, // 58
      mccCount_3, // 59
      classes, // 60
      constructors, // 61
      assignment, // 62
      returnStmt, // 63
      castStmt, // 64
      variables, // 65
      ooFields, // 66
      nestedForLoop, // 67
      nestedIteration, // 68
      functionName, // 71
      name, // 72
      type, // 73
      value, // 74
      myName; // 75

  /** xpath evaluation context. */
  protected final xv xv = new xv(this);

  {
    try {
      children = xv.xpath.compile("*"); // 47
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("children: cannot compile ["+xvE+"]");
    }
    try {
      childrenCount = xv.xpath.compile("count(*)"); // 48
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("childrenCount: cannot compile ["+xvE+"]");
    }
    try {
      functions = xv.xpath.compile("descendant::function"); // 51
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("functions: cannot compile ["+xvE+"]");
    }
    try {
      functionParams = xv.xpath.compile("descendant::automatic-Var[1]/var"); // 52
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("functionParams: cannot compile ["+xvE+"]");
    }
    try {
      functionReturnType = xv.xpath.compile("descendant::returns/@type"); // 53
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("functionReturnType: cannot compile ["+xvE+"]");
    }
    try {
      functionCalls = xv.xpath.compile("descendant::call"); // 54
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("functionCalls: cannot compile ["+xvE+"]");
    }
    try {
      variablesDef = xv.xpath.compile("descendant::var"); // 55
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("variablesDef: cannot compile ["+xvE+"]");
    }
    try {
      constantsUsed = xv.xpath.compile("descendant::constant"); // 56
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("constantsUsed: cannot compile ["+xvE+"]");
    }
    try {
      mccCount_1 = xv.xpath.compile("descendant::operator"); // 57
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("mccCount_1: cannot compile ["+xvE+"]");
    }
    try {
      mccCount_2 = xv.xpath.compile("descendant::for-loop"); // 58
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("mccCount_2: cannot compile ["+xvE+"]");
    }
    try {
      mccCount_3 = xv.xpath.compile("descendant::iterate"); // 59
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("mccCount_3: cannot compile ["+xvE+"]");
    }
    try {
      classes = xv.xpath.compile("descendant::class-type"); // 60
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("classes: cannot compile ["+xvE+"]");
    }
    try {
      constructors = xv.xpath.compile("descendant::constructor"); // 61
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("constructors: cannot compile ["+xvE+"]");
    }
    try {
      assignment = xv.xpath.compile("descendant::assign"); // 62
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("assignment: cannot compile ["+xvE+"]");
    }
    try {
      returnStmt = xv.xpath.compile("descendant::return"); // 63
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("returnStmt: cannot compile ["+xvE+"]");
    }
    try {
      castStmt = xv.xpath.compile("descendant::cast"); // 64
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("castStmt: cannot compile ["+xvE+"]");
    }
    try {
      variables = xv.xpath.compile("descendant::variable"); // 65
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("variables: cannot compile ["+xvE+"]");
    }
    try {
      ooFields = xv.xpath.compile("descendant::field"); // 66
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("ooFields: cannot compile ["+xvE+"]");
    }
    try {
      nestedForLoop = xv.xpath.compile("count(descendant-or-self::for-loop)"); // 67
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("nestedForLoop: cannot compile ["+xvE+"]");
    }
    try {
      nestedIteration = xv.xpath.compile("count(descendant-or-self::iterate)"); // 68
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("nestedIteration: cannot compile ["+xvE+"]");
    }
    try {
      functionName = xv.xpath.compile("./variable/@name"); // 71
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("functionName: cannot compile ["+xvE+"]");
    }
    try {
      name = xv.xpath.compile("./@name"); // 72
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("name: cannot compile ["+xvE+"]");
    }
    try {
      type = xv.xpath.compile("./@type"); // 73
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("type: cannot compile ["+xvE+"]");
    }
    try {
      value = xv.xpath.compile("./@value"); // 74
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("value: cannot compile ["+xvE+"]");
    }
    try {
      myName = xv.xpath.compile("name()"); // 75
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("myName: cannot compile ["+xvE+"]");
    }
  }

   /** selection trace. */
  protected static boolean xvVerbose = false;
  // right now this is copied in...
  /** runtime support; could be moved to an archive. */
  public static class xv {
    /** xpath compiler. */
    public final javax.xml.xpath.XPath xpath =
      javax.xml.xpath.XPathFactory.newInstance().newXPath();
    
    public xv (java.lang.Object visitor) {
      xpath.setXPathFunctionResolver(new FunctionResolver(visitor));
      xpath.setXPathVariableResolver(new VariableResolver(visitor));
    }
    
    /** allows xpaths to call methods in the visitor. */
    protected static class FunctionResolver
        implements javax.xml.xpath.XPathFunctionResolver {
      /** for reflection. */
      protected final java.lang.Object visitor;
      
      public FunctionResolver (java.lang.Object visitor) {
        this.visitor = visitor;
      }
      
      /** return a reflector: <tt>x:name</tt> is mapped to a method
          of the visitor, irrespective of <tt>x</tt>. */
      public javax.xml.xpath.XPathFunction resolveFunction (
          final javax.xml.namespace.QName qn, int i) {
        return new javax.xml.xpath.XPathFunction() {
          public java.lang.Object evaluate (java.util.List arg)
              throws javax.xml.xpath.XPathFunctionException {
            try {
              java.lang.Class[] parm = new java.lang.Class[arg.size()];
              for (int n = 0; n < parm.length; ++ n)
                parm[n] = arg.get(n).getClass();
              return visitor.getClass().getMethod(qn.getLocalPart(), parm)
                .invoke(visitor, arg.toArray());
            } catch (java.lang.Exception e) {
              throw new javax.xml.xpath.XPathFunctionException(e.getMessage());
            }
          }
        };
      }
    }
    
    /** allows xpaths to fetch fields in the visitor. */
    protected static class VariableResolver
        implements javax.xml.xpath.XPathVariableResolver {
      /** for reflection. */
      protected final java.lang.Object visitor;
      
      public VariableResolver (java.lang.Object visitor) {
        this.visitor = visitor;
      }

      /** reflect: <tt>x:name</tt> is mapped to a field
          of the visitor, irrespective of <tt>x</tt>. */
      public java.lang.Object resolveVariable (
          javax.xml.namespace.QName qn) {
        try {
          java.lang.Object value = visitor.getClass()
              .getField(qn.getLocalPart()).get(visitor);
          if (value instanceof java.lang.Boolean ||
              value instanceof java.lang.String ||
              value instanceof org.w3c.dom.Node ||
              value instanceof org.w3c.dom.NodeList)
            return value;
          if (value instanceof java.lang.Number)
            return (Double)((Number)value).doubleValue();
          return value.toString();
        } catch (java.lang.Exception e) {
          return null;
        }
      }
    }
    
    /** evaluates <tt>#</tt> items, see {@link #ref}. */
    protected interface Helper {
      java.lang.Object evaluate (javax.xml.xpath.XPathExpression p,
          org.w3c.dom.Node n) throws javax.xml.xpath.XPathExpressionException;
    }

    /** constant xpaths: <tt>name()</tt>, <tt>.</tt> */
    public final javax.xml.xpath.XPathExpression name, self;
    {
      try {
        name = xpath.compile("name()");
        self = xpath.compile(".");
      } catch (javax.xml.xpath.XPathExpressionException e) {
        throw new java.lang.Error("cannot compile constant xpaths");
      }
    }

    /** capture the result of matching a rule. */
    public class Matcher {
      /** matched node. */
      protected final org.w3c.dom.Node node;
      
      /** matched rule. */
      protected final Rule rule;
      
      /** matched right-hand side, if any. */
      protected final Rhs rhs;
      
      /** perform the match: delegate to each {@link Rule}.
          @param obj must be a {@link org.w3c.dom.Node}. */
      public Matcher (Rule[] rules, java.lang.Object obj) {
        try {
          node = (org.w3c.dom.Node)obj;
          if (xvVerbose) System.err.print(name.evaluate(node));
          Rhs rhs;
          for (Rule rule: rules)
            if ((rhs = rule.match(node)) != null) {
              this.rule = rule; this.rhs = rhs;
              if (xvVerbose) System.err.println();
              return;
            }
          throw 
            new java.lang.RuntimeException("("+rules[0].rhs[0].lineNumber+")"
            +" no suitable rule");
        } catch (javax.xml.xpath.XPathExpressionException e) {
          throw 
            new java.lang.RuntimeException("("+rules[0].rhs[0].lineNumber+")"
            +" match error ["+e+"]");
        } catch (java.lang.ClassCastException e) {
          throw 
            new java.lang.RuntimeException("("+rules[0].rhs[0].lineNumber+")"
            +" match error ["+e+"]");
        }
      }
      
      /** return {@link #rhs}.{@link Rhs#lineNumber}. */
      public int lineNumber () { return rhs.lineNumber; }
      
      /** wrap {@link #lineNumber} and a cause exception. */
      public java.lang.RuntimeException error (java.lang.Exception e) {
        java.lang.String at = "("+lineNumber()+") ";
        
        if (e == null) throw new java.lang.Error(at+"unexpected error");
        return new java.lang.RuntimeException(at+"error ["+e+"]");
      }

      /** maps type to evaluator. */
      protected final java.util.HashMap<java.lang.String,Helper> helper =
        new java.util.HashMap<java.lang.String, Helper>();
      {
        helper.put("Boolean", new Helper() {
          public Object evaluate (javax.xml.xpath.XPathExpression path,
              org.w3c.dom.Node node)
              throws javax.xml.xpath.XPathExpressionException {
            return (Boolean)path.evaluate(node,
              javax.xml.xpath.XPathConstants.BOOLEAN);
          }
        });
        helper.put("Double", new Helper() {
          public Object evaluate (javax.xml.xpath.XPathExpression path,
              org.w3c.dom.Node node)
              throws javax.xml.xpath.XPathExpressionException {
            return (Double)path.evaluate(node,
              javax.xml.xpath.XPathConstants.NUMBER);
          }
        });
        Helper first = new Helper() { // returns first node
          public Object evaluate (javax.xml.xpath.XPathExpression path,
              org.w3c.dom.Node node)
              throws javax.xml.xpath.XPathExpressionException {
            return (org.w3c.dom.Node)((org.w3c.dom.NodeList)path
              .evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0);
          }
        };
        helper.put(null, first);
        helper.put("Element", first);
        helper.put("NodeList", new Helper() {
          public Object evaluate (javax.xml.xpath.XPathExpression path,
              org.w3c.dom.Node node)
              throws javax.xml.xpath.XPathExpressionException {
            return (org.w3c.dom.NodeList)path.evaluate(node,
              javax.xml.xpath.XPathConstants.NODESET);
          }
        });
        helper.put("String", new Helper() {
          public Object evaluate (javax.xml.xpath.XPathExpression path,
              org.w3c.dom.Node node)
              throws javax.xml.xpath.XPathExpressionException {
            return path.evaluate(node);
          }
        });
      }
      
      /** value of untyped <tt>#</tt> item,
          delegate to <tt>ref("Node", item)</tt>. */
      java.lang.Object ref (int item)
          throws javax.xml.xpath.XPathExpressionException {
        return ref(null, item);
      }
      
      /** value of typed <tt>#</tt> item.
          @param null or Boolean Double Element NodeList String.
          @param item 0 for {@link #node}, other for descendants. */
      java.lang.Object ref (java.lang.String type, int item)
          throws javax.xml.xpath.XPathExpressionException {
        if (item == 0) // #<type>0
          return helper.get(type).evaluate(self, node);
        
        try {
          XCond cond = (XCond)rhs.cond[item-1]; // matched by xpath
          return helper.get(type).evaluate(cond.xpath, node);
          
        } catch (ClassCastException e) { // matched by regex
          org.w3c.dom.NodeList childNodes = node.getChildNodes();
          if (item > childNodes.getLength())
            throw new RuntimeException("no such child");
          return helper.get(type).evaluate(self, childNodes.item(item-1));
        }
      }
    }
    
    /** factory method. */
    public Rule rRule (java.lang.String regex, Rhs... rhs) {
      return new RRule(regex, rhs);
    }

    /** factory method. */
    public Rule xRule (java.lang.String xpath, Rhs... rhs) {
      return new XRule(xpath, rhs);
    }
    
    /** rule. */
    public abstract class Rule {
      /** right-hand sides, cannot be empty. */
      protected final Rhs[] rhs;
    
      protected Rule (Rhs[] rhs) { this.rhs = rhs; }
    
      /** (after subclass matches root) delegates to each {@link #rhs}.
          @return matched {@link Rhs} or null on failure. */
      public Rhs match (org.w3c.dom.Node node) {
        for (Rhs rhs: this.rhs)
          if (rhs.match(node)) return rhs;
        return null;
      }
    }

    /** contains a {@link java.util.regex.Pattern} to match the root element
        and a non-empty array of {@link Rhs} objects. */
    public class RRule extends Rule {
      protected final java.util.regex.Pattern regex;
      
      /** used by factory method. */
      protected RRule (java.lang.String regex, Rhs[] rhs) {
        super(rhs); this.regex = java.util.regex.Pattern.compile(regex);
      }
    
      /** if root is an element and {@link #regex} matches {@link xv#name} 
          delegate to <tt>super</tt>. */
      public Rhs match (org.w3c.dom.Node node) {
        try {
          if (xvVerbose) System.err.print(" "+rhs[0].lineNumber+"r");
          return regex.matcher(name.evaluate((org.w3c.dom.Element)node))
            .matches() ? super.match(node) : null;
        } catch (java.lang.ClassCastException e) {
          return null;
        } catch (javax.xml.xpath.XPathExpressionException e) {
          throw new java.lang.RuntimeException("("+rhs[0].lineNumber+")"
            +" root match error ["+e+"]");
        }
      }
    }

    /** contains an {@link javax.xml.xpath.XPathExpression} to match 
        the root node and a non-empty array of {@link Rhs} objects. */
    public class XRule extends Rule {
      protected final javax.xml.xpath.XPathExpression xpath;
    
      /** used by factory method. */
      protected XRule (java.lang.String xpath, Rhs[] rhs) {
        super(rhs);
        try {
          this.xpath = xv.this.xpath.compile(xpath);
        } catch (javax.xml.xpath.XPathExpressionException e) {
          throw new java.lang.Error("("+rhs[0].lineNumber+")"
            +" cannot compile ["+e+"]");
        }
      }
    
      /** if {@link #xpath} matches node delegate to <tt>super</tt>. */
      public Rhs match (org.w3c.dom.Node node) {
        try {
          if (xvVerbose) System.err.print(" "+rhs[0].lineNumber+"x");
          return (java.lang.Boolean)xpath.evaluate(node.getParentNode(),
            javax.xml.xpath.XPathConstants.BOOLEAN)
            ? super.match(node) : null;
        } catch (javax.xml.xpath.XPathExpressionException e) {
          throw new java.lang.RuntimeException("("+rhs[0].lineNumber+")"
            +" root match error ["+e+"]");
        }
      }
    }

    /** right-hand side of a rule. */
    public class Rhs {
      /** source line number. */
      protected final int lineNumber;
    
      /** array of conditions, can be empty. */
      protected final Cond[] cond;
    
      public Rhs (int lineNumber, Cond... cond) {
        this.lineNumber = lineNumber;
        this.cond = cond;
      }

      /** matches number of descendants, delegates to each {@link #cond}. */
      public boolean match (org.w3c.dom.Node node) {
        if (xvVerbose) System.err.print(" "+lineNumber);
        org.w3c.dom.NodeList childNodes = node.getChildNodes();
        
        for (int n = 0; n < cond.length; ++ n)
          if (!cond[n].match(this, node, childNodes, n)) return false;
        return true;
      }
    }

    /** factory method. */
    public Cond rCond (java.lang.String regex) {
      return new RCond(regex);
    }
    
    /** factory method. */
    public Cond xCond (java.lang.String xpath) {
      return new XCond(xpath);
    }
    
    /** condition. */
    public abstract class Cond {

      public abstract boolean match (Rhs rhs, org.w3c.dom.Node node,
          org.w3c.dom.NodeList childNodes, int item);
    }
      
    /** contains a {@link java.util.regex.Pattern} to match
        the descendant element. */
    public class RCond extends Cond {
      protected final java.util.regex.Pattern regex;
      
      /** used by factory method. */
      protected RCond (java.lang.String regex) {
        this.regex = java.util.regex.Pattern.compile(regex);
      }

      /** true if there is a descendant element and
          {@link #regex} matches {@link xv#name}. */
      public boolean match (Rhs rhs, org.w3c.dom.Node node,
          org.w3c.dom.NodeList childNodes, int item) {
        if (xvVerbose) System.err.print(" r"+item);
        
        // not a descendants?
        if (item >= childNodes.getLength()) return false;
        
        // matching element?
        try {
          return regex.matcher(name
            .evaluate((org.w3c.dom.Element)childNodes.item(item))).matches();
        } catch (java.lang.ClassCastException cce) {
          return false;
        } catch (javax.xml.xpath.XPathExpressionException xpe) {
          throw new java.lang.RuntimeException("("+rhs.lineNumber+")"
              +" descendant "+item+" match error ["+xpe+"]");
        }
      }
    }
        
    /** contains a boolean {@link javax.xml.xpath.XPathExpression}
        evaluated relative to the root. */
    public class XCond extends Cond {
      protected final javax.xml.xpath.XPathExpression xpath;
      
      /** used by factory method. */
      protected XCond (java.lang.String xpath) {
        try {
          this.xpath = xv.this.xpath.compile(xpath);
        } catch (javax.xml.xpath.XPathExpressionException e) {
          throw new java.lang.Error("'"+xpath+"'"+" cannot compile ["+e+"]");
        }
      }

      /** evaluates {@link #xpath} relative to the root. */
      public boolean match (Rhs rhs, org.w3c.dom.Node node,
          org.w3c.dom.NodeList childNodes, int item) {
        if (xvVerbose) System.err.print(" x"+item);
        try {
          return (java.lang.Boolean)xpath.evaluate(node,
            javax.xml.xpath.XPathConstants.BOOLEAN);
        } catch (javax.xml.xpath.XPathExpressionException e) {
          throw new java.lang.RuntimeException("("+rhs.lineNumber+")"
            +" condition "+item+" error ["+e+"]");
        }
      }
    }
  }
  /** one table per function body, named by source line */
  protected final xv.Rule[] xv278;
  {
    try {
      xv278 = new xv.Rule[] {
        xv.rRule(
          "uXML",
          xv.new Rhs(152)
        ),
        xv.rRule(
          "for-loop",
          xv.new Rhs(159)
        ),
        xv.rRule(
          "iterate",
          xv.new Rhs(166)
        ),
        xv.rRule(
          "function",
          xv.new Rhs(188)
        ),
        xv.rRule(
          "call",
          xv.new Rhs(196)
        ),
        xv.rRule(
          "var",
          xv.new Rhs(204)
        ),
        xv.rRule(
          "variable",
          xv.new Rhs(209)
        ),
        xv.rRule(
          "constant",
          xv.new Rhs(215)
        ),
        xv.rRule(
          "field",
          xv.new Rhs(220)
        ),
        xv.rRule(
          "operator",
          xv.new Rhs(231)
        ),
        xv.rRule(
          "assign",
          xv.new Rhs(236)
        ),
        xv.rRule(
          "return",
          xv.new Rhs(241)
        ),
        xv.rRule(
          "cast",
          xv.new Rhs(246)
        ),
        xv.rRule(
          "class-type",
          xv.new Rhs(260)
        ),
        xv.rRule(
          "constructor",
          xv.new Rhs(274)
        ),
        xv.xRule(
          "*",
          xv.new Rhs(277,
            xv.xCond("*")
          )
        )
      };
    } catch (java.lang.Exception xvE) {
      throw new java.lang.Error("(278) cannot compile ["+xvE+"]");
    }
  }

	
	private void visit(Node node) {
		int nodeListSize, index, functionsOverriden = 0;
		    xv.Matcher xvM = xv.new Matcher(xv278, node);
    try {
      switch (xvM.lineNumber()) {
      case 152:
{
			varDetailsToPrint = 2;
			printInformation(((org.w3c.dom.NodeList)functions.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),"Function defined : ",lined);
			
			isFunctionCall = true;
			printInformation(((org.w3c.dom.NodeList)functionCalls.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),"Function calls : ",lined);
			
			varDetailsToPrint = 3;
			printInformation(((org.w3c.dom.NodeList)variablesDef.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),"Variables defined : ",lined);
			
			printInformation(((org.w3c.dom.NodeList)constantsUsed.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),"Constants used: ",lined);
			
			calcMCC(((org.w3c.dom.NodeList)mccCount_1.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)), ((org.w3c.dom.NodeList)mccCount_2.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength(),((org.w3c.dom.NodeList)mccCount_3.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength(),"McCabe Cyclomatic Complexity : ");
			
			printInformation(((org.w3c.dom.NodeList)classes.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),"Classes defined : ",lined);
			
			StringBuffer functionOverridenInfo = new StringBuffer("\n");
			for(String nameOfFunction : overridenFunction.keySet())
				if(overridenFunction.get(nameOfFunction) > 1) {
					functionsOverriden++;
					functionOverridenInfo.append(nameOfFunction + "\n");
				}

			System.out.println("Functions overriden : " + functionsOverriden
					+ functionOverridenInfo.toString());
			
			nodeListSize = ((org.w3c.dom.NodeList)mccCount_2.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength();
			for(index = 0; index < nodeListSize; index++) {
				visit(((org.w3c.dom.NodeList)mccCount_2.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(index));
			}

			nodeListSize = ((org.w3c.dom.NodeList)mccCount_3.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength();
			for(index = 0; index < nodeListSize; index++) {
				visit(((org.w3c.dom.NodeList)mccCount_3.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(index));
			}

			System.out.println(lined);
			System.out.println("Nested Block Depth : " + (nestedForCount > nestedIterationCount ? nestedForCount : nestedIterationCount));
			
			System.out.println(lined);
			visitNodesInList(((org.w3c.dom.NodeList)assignment.evaluate(node, javax.xml.xpath.XPathConstants.NODESET))); visitNodesInList(((org.w3c.dom.NodeList)returnStmt.evaluate(node, javax.xml.xpath.XPathConstants.NODESET))); visitNodesInList(((org.w3c.dom.NodeList)castStmt.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)));
			visitNodesInList(((org.w3c.dom.NodeList)variables.evaluate(node, javax.xml.xpath.XPathConstants.NODESET))); visitNodesInList(((org.w3c.dom.NodeList)ooFields.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)));
			countHelsteadParams();
			System.out.println("Helstead Metrics :");
//			System.out.println(hmOperator);
//			System.out.println(hmOperand);
			System.out.println("N1 = " + N1 + ", n1 = " + n1 + ", N2 = " + N2 + ", n2 = " + n2);
			
			N = N1 + N2;
			System.out.println("Program Length N => N1 + N2 = " + N);
			n = n1 + n2;
			System.out.println("Voculbary Size n => n1 + n2 = " + n);
			V = N * Math.log((double)n)/Math.log(2);
			System.out.println("Program Volume V => N * log2(n) = " + V);
			D = ((double)n1 / 2.0) * ((double)N2 / (double)n2);
			System.out.println("Difficulty Level D => (n1 / 2) * (N2 / n2) = " + D);
			L = 1/D;
			System.out.println("Program Level L => 1/D = " + L);
			E = V * D;
			System.out.println("Effort to implement E => V*D = " + E);
			T = E / 18;
			System.out.println("Time to implement T = E/18 = " + T);
			B = Math.pow(E, (2/3))/3000;
			System.out.println("No. of bugs delivered B => E^(2/3)/3000 = " + B);
			
			System.out.println(lined);
			MI = 171.0 - 5.2 * Math.log(V) - 0.23 * MCC - 16.2 * Math.log(loc);
			System.out.println("Maintainibility Index M => 171 - 5.2 * ln(V) -0.23 * MCC -16.2 * ln(LOC) = " +  MI);
//			aveV = average Halstead Volume (CMT++/CMTJava s V) per module
//	        aveG = average extended cyclomatic complexity (CMT++/CMTJava s v(G) ) per module
//	        aveLOC = average count of lines (CMT++/CMTJava s LOCphy) per module
 
		}
        break;

      case 159:
{
			if(nestedForCount < ((java.lang.Double)nestedForLoop.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)).intValue())
				nestedForCount = ((java.lang.Double)nestedForLoop.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)).intValue();
			countOperator(((java.lang.String)myName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 166:
{
			if(nestedIterationCount < ((java.lang.Double)nestedIteration.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)).intValue())
				nestedIterationCount = ((java.lang.Double)nestedIteration.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)).intValue();
			countOperator(((java.lang.String)myName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 188:
{
			String nameOfFunction = ((java.lang.String)name.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).trim().length() == 0? Constants.LAMBDA : ((java.lang.String)name.evaluate(node, javax.xml.xpath.XPathConstants.STRING));
			System.out.print((((java.lang.String)functionReturnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).trim().length() == 0? Constants.VOID : ((java.lang.String)functionReturnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING))) + " ");
			System.out.print(nameOfFunction + "(");
			// Add the function in hashmap or if it is repeated, then increase the count.
			if(overridenFunction.containsKey(nameOfFunction))
				overridenFunction.put(nameOfFunction, overridenFunction.get(nameOfFunction) + 1);
			else
				overridenFunction.put(nameOfFunction, 1);
			
			nodeListSize = ((org.w3c.dom.NodeList)functionParams.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength();
			
			for(index = 0; index < nodeListSize; index++) {
				visit(((org.w3c.dom.NodeList)functionParams.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(index));
				if(nodeListSize - index > 1)
					System.out.print(",");
			}

			countOperand(nameOfFunction);
			System.out.println(")");
		}
        break;

      case 196:
{
			String calledFunction = Constants.CONSTRUCTOR.equals(((java.lang.String)functionName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))? "Constructor()" : ((java.lang.String)functionName.evaluate(node, javax.xml.xpath.XPathConstants.STRING));
			calledFunction = calledFunction.trim().length() == 0? "Call_Through_Operator" : calledFunction;
			countOperator(((java.lang.String)functionName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
			System.out.println(calledFunction);
		}
        break;

      case 204:
{
			if(varDetailsToPrint == 3)
				System.out.println(((java.lang.String)name.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) + " : " + ((java.lang.String)type.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
			else
				System.out.print(varDetailsToPrint == 1? ((java.lang.String)name.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) : ((java.lang.String)type.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 209:
{
			countOperand(((java.lang.String)name.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 215:
{
			System.out.println(((java.lang.String)type.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) + " : " + ((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
			countOperand(((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 220:
{
			countOperand(((java.lang.String)name.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 231:
{
			if(
				((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).equals("(") || ((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).equals("(=") || ((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).equals(")") ||
		        ((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).equals(")=") || ((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).equals("==") || ((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).equals("!=") ||
		        ((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).equals("$$") || ((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).equals("||") || ((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).equals("!")
		       ) mcc++;
				
			countOperator(((java.lang.String)value.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 236:
{
			countOperator(((java.lang.String)myName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 241:
{
			countOperator(((java.lang.String)myName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 246:
{
			countOperator(((java.lang.String)myName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 260:
{
			System.out.println("Class name : " + ((java.lang.String)name.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
			System.out.println();
			
			varDetailsToPrint = 3;
			printInformation(((org.w3c.dom.NodeList)variablesDef.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),"Variables defined : ",dashed);
			
			varDetailsToPrint = 2;
			printInformation(((org.w3c.dom.NodeList)functions.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),"Function defined : ",dashed);
			
			printInformation(((org.w3c.dom.NodeList)constructors.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),"Constructors defined : ",lined);
		}
        break;

      case 274:
{
			System.out.print(((java.lang.String)name.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) + "(");
			nodeListSize = ((org.w3c.dom.NodeList)functionParams.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength();
			
			for(index = 0; index < nodeListSize; index++) {
				visit(((org.w3c.dom.NodeList)functionParams.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(index));
				if(nodeListSize - index > 1)
					System.out.print(",");
			}

			System.out.println(")");
			countOperand(((java.lang.String)name.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
		}
        break;

      case 277:
{}
        break;

      default: // cannot happen
        throw xvM.error(null);
      }
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw xvM.error(xvE);
    }
	}
	
	/**
	 * Prints the information
	 */
	private void printInformation(NodeList nodeList, String msg, String terminator) {
		int nodeListSize = nodeList.getLength();
		System.out.println(msg + nodeListSize);
		for(int index = 0; index < nodeListSize; index++) {
			visit(nodeList.item(index));
		}
		System.out.println(terminator);
	}
	
	private void visitNodesInList(NodeList nodeList) {
		int nodeListSize = nodeList.getLength();
		for(int index = 0; index < nodeListSize; index++) {
			visit(nodeList.item(index));
		}
	}
	
	private void calcNestedBlockDepth(NodeList forLoopList, NodeList iterationList, 
			NodeList nestedFor, NodeList nestedIteration, String msg) {
		int maxDepth = 0;
	}
	
	/**
	 * Calculates the Mccabe Cyclomatic Complexity
	 */
	private void calcMCC(NodeList nodeList, int for_loop, int iterate_loop, String msg) {
		int nodeListSize = nodeList.getLength();
		System.out.print(msg);
		for(int index = 0; index < nodeListSize; index++) {
			visit(nodeList.item(index));
		}
		MCC = mcc + for_loop + iterate_loop;
		System.out.println((mcc + for_loop + iterate_loop));
		System.out.println("____________________________________________");
	}
	
	
	/**
	 * Counts the operator by adding it in hashmap if doesn't exist
	 */
	private void countOperator(String operator) {
		Integer count = hmOperator.get(operator);
		hmOperator.put(operator,(count == null?1 : count + 1));
	}
	
	/**
	 * Counts the operand by adding it in hashmap if doesn't exist
	 */
	private void countOperand(String operand) {
		Integer count = hmOperand.get(operand);
		hmOperand.put(operand,(count == null?1 : count + 1));
	}
	
	/**
	 * Counts the values N1, n1, N2, n2 required to calculate Helstead Metrics.
	 */
	private void countHelsteadParams() {
		n1 = hmOperator.size(); n2 = hmOperand.size();
		Set<String> keys = hmOperator.keySet();
		for(String operator : keys) {
			N1 += hmOperator.get(operator);
		}
		keys = hmOperand.keySet();
		for(String operand : keys) {
			N2 += hmOperand.get(operand);
		}
	}
	
	/**
	 * Currently passing loc as the 2nd argument. A method can be introduced that will be a wrapper to take this parameter.
	 */
	public static void main(String args[], int loc) {
		CodeAnalysis codeAnalysis = new CodeAnalysis();
		codeAnalysis.loc = loc;
	        
        // get document
        Document document = null;
        String fileName = null;
        try {
            if(args.length == 0) {
            	Scanner in = new Scanner(System.in);
            	System.out.print("Enter the file name @ src/sampleuXMLProgs/");
            	fileName = in.nextLine();
            }
            else {
                fileName = args[0];   
            }
            
            fileName = "src" + File.separator + "sampleuXMLProgs" + File.separator + fileName + ".xml";
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);
            
            // remove trivial text nodes
            NodeList list = (NodeList)codeAnalysis.xv.xpath.compile("//text()[not(normalize-space(.))]").evaluate(document, XPathConstants.NODESET);    
            for (int n = list.getLength(); --n >= 0; )
              list.item(n).getParentNode().removeChild(list.item(n));
        
            // remove trivial comments nodes
            list = (NodeList)codeAnalysis.xv.xpath.compile("//comment()").evaluate(document, XPathConstants.NODESET);
            for (int n = list.getLength(); --n >= 0; )
              list.item(n).getParentNode().removeChild(list.item(n));
            
            
            // Start visiting each node of uXML
            codeAnalysis.visit(document.getDocumentElement());
        
        } catch (SAXException e) { 
			System.err.println("Problem parsing file " + args[0]);
		} catch (IOException e) {
			System.err.println("Problem opening file " + args[0]);
		} catch (ParserConfigurationException e) {
			System.err.println("Problem with the parser configuration, while parsing file " + args[0]);
		} catch (XPathExpressionException e) {
			System.err.println("Problem in xPath expression.");
		} catch (Exception e) {
			System.err.println("Please fix the compilation errors.\n" + e.getMessage());
		} 
	}
}
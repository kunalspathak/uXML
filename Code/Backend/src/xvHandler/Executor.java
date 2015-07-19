// created by xv 1.0.2 (c) 2009 axel.schreiner@rit.edu
package xvHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.Scanner;
import compiler.dataTypes.ConstantValue;
import compiler.components.Symbol;
import compiler.components.Environment;
import compiler.components.BuiltInFunctions;
import compiler.dataTypes.FunctionType;
import compiler.dataTypes.PointerType;
import compiler.dataTypes.ArrayType; 
import compiler.dataTypes.OverloadedFunctionType;
import compiler.dataTypes.ClassType;
import compiler.operator.Operator;
import compiler.dataTypes.SymbolMachine;
import compiler.util.Constants;
import compiler.util.Constants.FUNCTION_TYPE;
import compiler.uXMLGenerator.UXMLOOCodeGenerator;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import compiler.exceptions.*;

public class Executor {
    // current symbol to be returned
    Symbol returnSymbol = null;
    // current function nested level
    int currentNestedLevel = -1;
    boolean isAlreadyEntered = false;
    PrintStream out = null;
    protected final
        javax.xml.xpath.XPathExpression
      children, // 43
      argumentsList, // 45
      formalArguments, // 46
      nestedLevelofFunction, // 47
      functionName, // 48
      functionBody, // 49
      returnType, // 50
      returnFunctionType, // 51
      ancestors, // 53
      memberVariables, // 54
      constructors, // 55
      memberFunctions, // 56
      mainFunction, // 57
      callToParentClass, // 58
      argsToSuperClassCall, // 59
      scopeList, // 60
      scopeType, // 61
      polymorphism, // 62
      varDeclaration, // 63
      childrenCount, // 64
      conditionalBlock, // 65
      attributeName, // 67
      attributeType, // 68
      attributeMutable, // 69
      attributeValue, // 70
      attributeRefType, // 71
      attributePtrMutable, // 72
      attributeValueMutable, // 73
      arrayType, // 74
      arraySize, // 75
      arrayStartIndex; // 76

  /** xpath evaluation context. */
  protected final xv xv = new xv(this);

  {
    try {
      children = xv.xpath.compile("*"); // 43
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("children: cannot compile ["+xvE+"]");
    }
    try {
      argumentsList = xv.xpath.compile("./automatic-Var/var"); // 45
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("argumentsList: cannot compile ["+xvE+"]");
    }
    try {
      formalArguments = xv.xpath.compile("./automatic-Var[1]"); // 46
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("formalArguments: cannot compile ["+xvE+"]");
    }
    try {
      nestedLevelofFunction = xv.xpath.compile("count(ancestor::function)"); // 47
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("nestedLevelofFunction: cannot compile ["+xvE+"]");
    }
    try {
      functionName = xv.xpath.compile("./variable[1]/@name"); // 48
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("functionName: cannot compile ["+xvE+"]");
    }
    try {
      functionBody = xv.xpath.compile("./block[1]"); // 49
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("functionBody: cannot compile ["+xvE+"]");
    }
    try {
      returnType = xv.xpath.compile("./returns[1]/@type"); // 50
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("returnType: cannot compile ["+xvE+"]");
    }
    try {
      returnFunctionType = xv.xpath.compile("./returns[1]/@value"); // 51
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("returnFunctionType: cannot compile ["+xvE+"]");
    }
    try {
      ancestors = xv.xpath.compile("child::inherits/*"); // 53
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("ancestors: cannot compile ["+xvE+"]");
    }
    try {
      memberVariables = xv.xpath.compile("child::member-Var/*"); // 54
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("memberVariables: cannot compile ["+xvE+"]");
    }
    try {
      constructors = xv.xpath.compile("child::constructor"); // 55
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("constructors: cannot compile ["+xvE+"]");
    }
    try {
      memberFunctions = xv.xpath.compile("child::function"); // 56
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("memberFunctions: cannot compile ["+xvE+"]");
    }
    try {
      mainFunction = xv.xpath.compile("//function"); // 57
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("mainFunction: cannot compile ["+xvE+"]");
    }
    try {
      callToParentClass = xv.xpath.compile("./block[1]/call[1]/variable[1]/@name"); // 58
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("callToParentClass: cannot compile ["+xvE+"]");
    }
    try {
      argsToSuperClassCall = xv.xpath.compile("./block[1]/call[1]/*"); // 59
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("argsToSuperClassCall: cannot compile ["+xvE+"]");
    }
    try {
      scopeList = xv.xpath.compile("./scope[1]"); // 60
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("scopeList: cannot compile ["+xvE+"]");
    }
    try {
      scopeType = xv.xpath.compile("./@scope"); // 61
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("scopeType: cannot compile ["+xvE+"]");
    }
    try {
      polymorphism = xv.xpath.compile("./@poly"); // 62
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("polymorphism: cannot compile ["+xvE+"]");
    }
    try {
      varDeclaration = xv.xpath.compile("./@var-declaration"); // 63
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("varDeclaration: cannot compile ["+xvE+"]");
    }
    try {
      childrenCount = xv.xpath.compile("count(*)"); // 64
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("childrenCount: cannot compile ["+xvE+"]");
    }
    try {
      conditionalBlock = xv.xpath.compile("name(child::*[2])"); // 65
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("conditionalBlock: cannot compile ["+xvE+"]");
    }
    try {
      attributeName = xv.xpath.compile("./@name"); // 67
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("attributeName: cannot compile ["+xvE+"]");
    }
    try {
      attributeType = xv.xpath.compile("./@type"); // 68
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("attributeType: cannot compile ["+xvE+"]");
    }
    try {
      attributeMutable = xv.xpath.compile("./@mutable"); // 69
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("attributeMutable: cannot compile ["+xvE+"]");
    }
    try {
      attributeValue = xv.xpath.compile("./@value"); // 70
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("attributeValue: cannot compile ["+xvE+"]");
    }
    try {
      attributeRefType = xv.xpath.compile("./@refType"); // 71
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("attributeRefType: cannot compile ["+xvE+"]");
    }
    try {
      attributePtrMutable = xv.xpath.compile("./@ptrMutable"); // 72
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("attributePtrMutable: cannot compile ["+xvE+"]");
    }
    try {
      attributeValueMutable = xv.xpath.compile("./@valueMutable"); // 73
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("attributeValueMutable: cannot compile ["+xvE+"]");
    }
    try {
      arrayType = xv.xpath.compile("../@type"); // 74
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("arrayType: cannot compile ["+xvE+"]");
    }
    try {
      arraySize = xv.xpath.compile("./@size"); // 75
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("arraySize: cannot compile ["+xvE+"]");
    }
    try {
      arrayStartIndex = xv.xpath.compile("./@startIndex"); // 76
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw new java.lang.Error("arrayStartIndex: cannot compile ["+xvE+"]");
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
  protected final xv.Rule[] xv901;
  {
    try {
      xv901 = new xv.Rule[] {
        xv.rRule(
          "uXML",
          xv.new Rhs(95)
        ),
        xv.rRule(
          "constant",
          xv.new Rhs(105,
            xv.xCond("count(*) = 0")
          )
        ),
        xv.rRule(
          "variable",
          xv.new Rhs(137,
            xv.xCond("count(*) = 0")
          )
        ),
        xv.rRule(
          "cast",
          xv.new Rhs(152)
        ),
        xv.rRule(
          "global-Var",
          xv.new Rhs(159)
        ),
        xv.rRule(
          "var",
          xv.new Rhs(235)
        ),
        xv.rRule(
          "pointer-type",
          xv.new Rhs(261)
        ),
        xv.rRule(
          "array-type",
          xv.new Rhs(305)
        ),
        xv.rRule(
          "dim",
          xv.new Rhs(317)
        ),
        xv.rRule(
          "array",
          xv.new Rhs(333)
        ),
        xv.rRule(
          "class-type",
          xv.new Rhs(373)
        ),
        xv.rRule(
          "constructor",
          xv.new Rhs(424)
        ),
        xv.rRule(
          "field",
          xv.new Rhs(437)
        ),
        xv.rRule(
          "assign",
          xv.new Rhs(454,
            xv.xCond("count(*) >= 2"),
            xv.xCond("count(*) <= 4")
          )
        ),
        xv.rRule(
          "operator",
          xv.new Rhs(478)
        ),
        xv.rRule(
          "for-loop",
          xv.new Rhs(489,
            xv.xCond("from"),
            xv.xCond("till"),
            xv.xCond("step"),
            xv.xCond("block")
          )
        ),
        xv.rRule(
          "from",
          xv.new Rhs(494,
            xv.rCond("assign")
          )
        ),
        xv.rRule(
          "till",
          xv.new Rhs(499,
            xv.rCond("operator")
          )
        ),
        xv.rRule(
          "step",
          xv.new Rhs(504,
            xv.rCond("assign")
          )
        ),
        xv.rRule(
          "iterate",
          xv.new Rhs(514,
            xv.rCond("block"),
            xv.rCond("till")
          ),
          xv.new Rhs(521,
            xv.rCond("till"),
            xv.rCond("block")
          )
        ),
        xv.rRule(
          "function-type",
          xv.new Rhs(545)
        ),
        xv.rRule(
          "function",
          xv.new Rhs(617)
        ),
        xv.rRule(
          "call",
          xv.new Rhs(825)
        ),
        xv.rRule(
          "return",
          xv.new Rhs(847)
        ),
        xv.rRule(
          "block",
          xv.new Rhs(864)
        ),
        xv.rRule(
          "print",
          xv.new Rhs(878)
        ),
        xv.rRule(
          "println",
          xv.new Rhs(891)
        ),
        xv.xRule(
          "*",
          xv.new Rhs(900,
            xv.xCond("*")
          )
        )
      };
    } catch (java.lang.Exception xvE) {
      throw new java.lang.Error("(901) cannot compile ["+xvE+"]");
    }
  }

    /**
     * This method visits the node that it gets as an argument and matches with appropriate 
     * rule. Once the rule is matched and guard conditions are checked, the action associated
     * with the corresponding guard condition is executed. 
     *
     */
    public Symbol visit(Node node) {
            xv.Matcher xvM = xv.new Matcher(xv901, node);
    try {
      switch (xvM.lineNumber()) {
      case 95:
{
                Environment.scopeType = ((java.lang.String)scopeType.evaluate(node, javax.xml.xpath.XPathConstants.STRING));
                Environment.isPolymorphism = Constants.YES.equals(((java.lang.String)polymorphism.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))? true:false;
                Environment.isVarMandatory = Constants.NO.equals(((java.lang.String)varDeclaration.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))? false:true;
                for(int childNo = 0; childNo < ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER));childNo++)
                    visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(childNo));
            }
        break;

      case 105:
{
                try 
                {
                    ConstantValue constant = new ConstantValue(((java.lang.String)attributeType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)),((java.lang.String)attributeValue.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
                    returnSymbol = constant;
                }
 catch (IllegalSymbolValueException e) { System.err.println(e.getMessage()); returnSymbol = null; }
 
            }
        break;

      case 137:
{
                try {
                	// If this is an OO language and variable name is 'this' it means that currentClass is already known
                	// and the variable is requesting for current class's access, so return it.
                	// However if currentClass is not known and still 'this' is used, throw the error
                	if(Environment.isPolymorphism && Constants.THIS.equals(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))) {
                			if(Environment.currentClass != null)
                    			returnSymbol = Environment.currentClass;
                    		else {
                    			System.err.println("'this' can be only used inside the class.");
                    			returnSymbol = null;
                    		}
	
                	}

                	 /*if direct access to current class's variable 
                	else if(Environment.isPolymorphism && Environment.currentClass != null) 
            		{
            			try {
                    		returnSymbol = Environment.currentClass.getSymbol($attributeName);
                    	} catch (SymbolNotDefinedException e) {
                    		System.err.println(e.getMessage());
                    		returnSymbol = null;
                    	}
            		} */
                	else
	                    // Starting searching for variables in current activation record and if not found, follow the 
	                    // static/dynamic link depending on the scope type
                		returnSymbol = Environment.getSymbol(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
                }
 catch(SymbolNotDefinedException e) { System.err.println(e.getMessage()); returnSymbol = null; }

            }
        break;

      case 152:
{
                // Read the variable to be type-casted
                returnSymbol = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0));
                if(returnSymbol != null) {
	                // Read the data-type in which above variable to be type-casted
	                try {
	                    returnSymbol = SymbolMachine.cast(returnSymbol, ((java.lang.String)attributeType.evaluate(node, javax.xml.xpath.XPathConstants.STRING))); 
	                }
 catch (IllegalSymbolValueException e) { returnSymbol = null; System.err.println(e.getMessage()); }
 
	                catch (TypeMismatchException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

					catch(SymbolNotDefinedException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

                }

            }
        break;

      case 159:
{
            	for(int childNo = 0; childNo < ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER));childNo++)
                    visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(childNo));
            }
        break;

      case 235:
{
               try 
               {
                    Symbol newVariable = null;
                    // Check if there is an immediate assignment to the declared variable 
                    if(((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)) == 1 && ((org.w3c.dom.NodeList)scopeList.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength() == 0 || ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)) == 2) {
                    	// Generate the mutable variable 
                    	newVariable = SymbolMachine.generate(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)),((java.lang.String)attributeType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)),true);
                    	
                    	// If this is a pointer, then check the values of ptrMutable and valueMutable and update newVariable
                        // accordingly.
                        if(newVariable instanceof PointerType) {
    	                    // Set value mutable
    	                    if(((java.lang.String)attributeValueMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) != null && ((java.lang.String)attributeValueMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).trim().length() > 0)
    	                    	((PointerType)newVariable).setPointeeMutable(Constants.YES.equals(((java.lang.String)attributeValueMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))?true:false);
                        }

                    	// Read the value to be assigned to this variable
                    	returnSymbol = (((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)) == 1)?visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0)):visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(1));
                    	// Set the value to this variable
                    	try { newVariable.setValue(returnSymbol); 
                    	}
 catch(ImmutableSymbolException e) {
                    		// Since we are making newVariable as mutable, this exception can occur
                    		// only if this is a pointer constant and user code tries to assign a constant
                    		// variable to the constant pointer.
                    		System.err.println(e.getMessage()); newVariable = null;
                    	}
 catch(TypeMismatchException e) { 
                    		System.err.println(e.getMessage()); newVariable = null; 
                    	}

                    	// Set the mutable status of this varaible.
                    	if(newVariable instanceof PointerType) {
                    		// Set pointer mutable
    	                    if(((java.lang.String)attributePtrMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) != null && ((java.lang.String)attributePtrMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).trim().length() > 0)
    	                    	((PointerType)newVariable).setMutableStatus(Constants.YES.equals(((java.lang.String)attributePtrMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))?true:false);
                        }

                    	else
                    		newVariable.setMutableStatus(Constants.YES.equals(((java.lang.String)attributeMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))?true:false);
                    	
                    }
 else {
                    	// There is no initialization of this variable, hence simply generate a new variable.
                    	newVariable = SymbolMachine.generate(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)),((java.lang.String)attributeType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)),Constants.YES.equals(((java.lang.String)attributeMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))?true:false);
                    	
                    	 // If this is a pointer, then check the values of ptrMutable and valueMutable and update newVariable
                        // accordingly.
                        if(newVariable instanceof PointerType) {
                        	// Set pointer mutable
    	                    if(((java.lang.String)attributePtrMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) != null && ((java.lang.String)attributePtrMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).trim().length() > 0)
    	                    	((PointerType)newVariable).setMutableStatus(Constants.YES.equals(((java.lang.String)attributePtrMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))?true:false);
    	                    
    	                    // Set value mutable
    	                    if(((java.lang.String)attributeValueMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) != null && ((java.lang.String)attributeValueMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).trim().length() > 0)
    	                    	((PointerType)newVariable).setPointeeMutable(Constants.YES.equals(((java.lang.String)attributeValueMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))?true:false);
                        }

                        // If this is an array, set the name of variable, since it was set to the array-type 
                        // in SymbolMachine.generate() method.  
                        if(newVariable instanceof ArrayType) {
                        	newVariable.setName(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
                        }

                    }

                    
                    // If this variable declaration is present at nestedLevel as that of current activation 
                    // record, then add the variable in current activation record, else skip
                    if(Environment.currentActivationRecord.getNestedLevel() == currentNestedLevel) {
                    	// If class definition then add the symbols in current class
            			// SymbolNotDefinedException will never occur for parameters, it is just
            			// throw for constructor
            			if(Environment.isClassDefinition)
            				Environment.currentClass.addSymbol(newVariable); 
            			else
            				Environment.currentActivationRecord.addSymbol(newVariable);
                    }

                    returnSymbol = newVariable;
                }
 catch (IllegalSymbolValueException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

            }
        break;

      case 261:
{
            	PointerType newPointerType = null;
            	try {
                	// Make it as non-mutable.
            		newPointerType = (PointerType)SymbolMachine.generate(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), Constants.POINTER, false);
            		// Create the data-type depending on the reference type and set as this pointer's reference type.
            		newPointerType.setReferenceType(((java.lang.String)attributeRefType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
            		// Set the initial pointee value
            		newPointerType.setPointee(SymbolMachine.generate(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), ((java.lang.String)attributeRefType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), true));
            		
            		// If class definition then add the symbols in current class
        			// SymbolNotDefinedException will never occur for parameters, it is just
        			// throw for constructor
        			if(Environment.isClassDefinition)
        				Environment.currentClass.addSymbol(newPointerType);
        			else
        				Environment.currentActivationRecord.addSymbol(newPointerType);
        			
            	}
 catch (IllegalSymbolValueException e) { System.err.println(e.getMessage()); }
 
            	catch (TypeMismatchException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

				catch (ImmutableSymbolException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

            	returnSymbol = newPointerType;
            }
        break;

      case 305:
{
            	try {
            		// Start constructing the array. Since last dimension's details are in the end,
                	// build an array in bottom-up fashion
                	ArrayList<ArrayType> newArrayType = new ArrayList<ArrayType>();
                	newArrayType.add((ArrayType)visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0)));
                	// If only 1D array is there, then initialize it, else continue reading rest of the
                	// dimensions
                	if(((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)) == 1) {
                		newArrayType.get(0).setValue(SymbolMachine.generate(Constants.BLANK, ((java.lang.String)attributeType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), true));
                	}

                	else {
	                	ArrayType arrayElement = null;
		            	for(int dimension = 1; dimension < ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)); dimension++)
		            	{
		            		arrayElement = (ArrayType)visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(dimension));
		            		// If this is the last dimension, then set the array of primitive data-type
		            		if(((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)) - dimension == 1)
		            			arrayElement.setValue(SymbolMachine.generate(Constants.BLANK, ((java.lang.String)attributeType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), true));
		            		newArrayType.add(arrayElement);
		            	}

		            	
		            	// Reset the values of array  in reverse order
		            	for(int dimension = ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)).intValue() - 1; dimension > 0; dimension--) 
		            		newArrayType.get(dimension - 1).setValue(newArrayType.get(dimension));
                	}

                	// The array definition is present in newArrayType's 0th element.
	            	returnSymbol = newArrayType.get(0);
	            	returnSymbol.setName(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
	            	
	            	// If class definition then add the symbols in current class
	    			// SymbolNotDefinedException will never occur for parameters, it is just
	    			// throw for constructor
	    			if(Environment.isClassDefinition)
	    				Environment.currentClass.addSymbol(returnSymbol); 
	    			else
	    				Environment.currentActivationRecord.addSymbol(returnSymbol);
	            	
            	}
 catch (IllegalSymbolValueException e) { }

            	catch (ImmutableSymbolException e) { }
 
            	catch (TypeMismatchException e) { }
 
            }
        break;

      case 317:
{
            	try 
                {
            		// Make the array non-mutable.
            		ArrayType currentDim = (ArrayType)SymbolMachine.generate(Constants.BLANK, Constants.ARRAY, false);
            		currentDim.setSize(((java.lang.Double)arraySize.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)).intValue());
            		currentDim.setStartIndex(((java.lang.Double)arrayStartIndex.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)).intValue());
            		returnSymbol =  currentDim;
                }
 catch (IllegalSymbolValueException e) {System.err.println(e.getMessage()); returnSymbol = null;}

            }
        break;

      case 333:
{
            	try {
            		// Create an array
            		ArrayType array = (ArrayType)SymbolMachine.generate(Constants.BLANK, Constants.ARRAY, false);
            		for(int elementNo = 0; elementNo < ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)); elementNo++) {
            			array.appendElement(visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(elementNo)));
            		}

            		ConstantValue constantArray = new ConstantValue(Constants.DYNAMIC, true);
            		constantArray.setValue(array);
            		returnSymbol = constantArray;
            	}
 catch (IllegalSymbolValueException e) {System.err.println(e.getMessage()); returnSymbol = null;}

            	catch (TypeMismatchException e) {System.err.println(e.getMessage()); returnSymbol = null;}

            }
        break;

      case 373:
{
            	try {
	            	// Create a new instance of class-type
	            	ClassType currentClass = (ClassType)SymbolMachine.generate(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), Constants.CLASS, Constants.YES.equals(((java.lang.String)attributeMutable.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))?true:false);
	            	// Assign same name since this is just a class-type 
	            	currentClass.setClassName(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
	            	
	            	// Add all the inheriting classes as ancestors
	            	for(int ancestorNo = 0; ancestorNo < ((org.w3c.dom.NodeList)ancestors.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength(); ancestorNo++) {
	            		currentClass.addAncestor((ClassType)visit(((org.w3c.dom.NodeList)ancestors.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(ancestorNo)));
	            	}

	            	// Switch to make sure that symbols are stored in this class and not in activation record
	            	Environment.isClassDefinition = true;
	            	Environment.currentClass = currentClass;
	            	
	            	// Add all the member variables in current class 
	            	for(int memberVarNo = 0; memberVarNo < ((org.w3c.dom.NodeList)memberVariables.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength(); memberVarNo++) {
	            		visit(((org.w3c.dom.NodeList)memberVariables.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(memberVarNo));
	            	}

	            	
	            	// Add all the constructors in current class 
	            	for(int constructorNo = 0; constructorNo < ((org.w3c.dom.NodeList)constructors.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength(); constructorNo++) {
	            		visit(((org.w3c.dom.NodeList)constructors.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(constructorNo));
	            	}

	            	
	            	// Add all the member functions in current class 
	            	for(int functionNo = 0; functionNo < ((org.w3c.dom.NodeList)memberFunctions.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength(); functionNo++) {
	            		visit(((org.w3c.dom.NodeList)memberFunctions.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(functionNo));
	            	}

	            	// Switch to make sure that symbols are stored in activation record and not in this class  
	            	Environment.isClassDefinition = false;
	            	Environment.currentClass = null;
	            	Environment.currentActivationRecord.addSymbol(currentClass);
	            	returnSymbol = currentClass;
	            }
 catch (IllegalSymbolValueException e) { System.err.println(e.getMessage()); returnSymbol = null; }
 
	            catch (SymbolNotDefinedException e) { System.err.println(e.getMessage()); returnSymbol = null; }

            }
        break;

      case 424:
{
            	FunctionType constructor = null;
            	Node constructorBody = null;
            	
            	String currentClassName = Environment.currentClass.getClassName();
            	// Check if constructor name is same as current class name
            	if(!((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).equals(currentClassName)) {
            		System.err.println("Constructor should be defined for class '" + currentClassName + "' and not for class '" + ((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING))+ "'");
            		return null;
            	}

            	// Make constructor non-mutable
            	constructor = new FunctionType(Constants.CONSTRUCTOR, false,Constants.VOID);
        		constructor.setNestedLevel(0);
            	
            	// If this constructor takes arguments, then set them in constructor definition 
                if(((org.w3c.dom.NodeList)formalArguments.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength() == 1) {
                    // Add formal parameters in function 
                    for(int argsNo = 0; argsNo < ((org.w3c.dom.NodeList)argumentsList.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength();argsNo++)
                    	constructor.addSymbol(visit(((org.w3c.dom.NodeList)argumentsList.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(argsNo)));
                }

                constructorBody = ((org.w3c.dom.NodeList)functionBody.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0); 
                
                // If this is a derived class and if there is no call to super class's 
                // constructor then insert a call to default constructor of parent class
                // as the first line of constructorBody
                if(!Environment.currentClass.isBaseClass() && !Constants.SUPER_CLASS.equals(((java.lang.String)callToParentClass.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))) {
                	Node firstLineInConstructor = constructorBody.getFirstChild();
                	Node callToSuperClass = UXMLOOCodeGenerator.generateCallToSuper(constructorBody.getOwnerDocument());
                	constructorBody.insertBefore(callToSuperClass, firstLineInConstructor);
                }

                
                ArrayList<Symbol> argsToSuper = new ArrayList<Symbol>();
                
                // Fetch the arguments passed to super class constructor
                // Since this is a derived class, there is a call to super class constructor
                if(!Environment.currentClass.isBaseClass()) {
	                for(int args = 2; args < ((org.w3c.dom.NodeList)argsToSuperClassCall.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength(); args++) {
	                	argsToSuper.add(visit(((org.w3c.dom.NodeList)argsToSuperClassCall.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(args)));
	                }

                }

                // Store the function body 
                constructor.setFunctionBody(constructorBody);
                try {
                	// add the constructor in current class
                	Environment.currentClass.addConstructor(constructor, argsToSuper);
                	returnSymbol = constructor;
                }
 catch(SymbolNotDefinedException e) { System.err.println(e.getMessage()); returnSymbol = null; }

            }
        break;

      case 437:
{
            	// Fetch the object whose variable is to be accessed
            	ClassType currentClass = (ClassType)visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0));
            	try {
            		returnSymbol = currentClass.getSymbol(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
            	}
 catch (SymbolNotDefinedException e) {
            		System.err.println(e.getMessage());
            		returnSymbol = null;
            	}

            }
        break;

      case 454:
{
                Symbol lhs = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0));
                Symbol rhs = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(1));
                try {
                	if(lhs == null) {
                		System.err.println("Trying to assign the value '" + rhs + "' to a null variable.");
                		return null;
                	}

					lhs.setValue(rhs);
				}
  catch (IllegalSymbolValueException e)  { returnSymbol = null; System.err.println(e.getMessage()); }
	
				catch (TypeMismatchException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

				catch(ImmutableSymbolException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

                returnSymbol = rhs;  // Return the last evaluated value
            }
        break;

      case 478:
{
                    if(Constants.ARITHMETIC_OPERATORS.contains(((java.lang.String)attributeValue.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))) {
                    	returnSymbol = processArithmeticOperator(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),((java.lang.String)attributeValue.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), false);
                    }
  
                    
                    // Logical Not
                    else if (Constants.LOGICAL_NOT.equals(((java.lang.String)attributeValue.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))) {
                    	
                    	returnSymbol =  processLogicalNot(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),((java.lang.String)attributeValue.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), false);
                    }

                    
                    // Conditional operator
                    else if(Constants.CONDITIONAL_OPERATORS.contains(((java.lang.String)attributeValue.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))) {
                    	returnSymbol = processConditionalOperator(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)), ((java.lang.String)attributeValue.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), ((java.lang.String)conditionalBlock.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), false);
                    }
 

                    // Functional operator
                    else if(Constants.FUNCTIONAL_OPERATORS.contains(((java.lang.String)attributeValue.evaluate(node, javax.xml.xpath.XPathConstants.STRING)))) {
                    	returnSymbol = processFunctionalOperator(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)), ((java.lang.String)attributeValue.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), false);
                    }

            }
        break;

      case 489:
{
                visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0));                                            // 'from'
                while(((ConstantValue)visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(1))).booleanValue()) {    // 'to'
                	// Return the last evaluated value
        	        returnSymbol = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(3));                         // 'block'
        	        visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(2));                                        // 'step'
                }

            }
        break;

      case 494:
{
            	visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0)); 
            }
        break;

      case 499:
{
                visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0));
            }
        break;

      case 504:
{
                visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0));
            }
        break;

      case 514:
{
                do 
                {
                	// Return the last evaluated value
                    returnSymbol = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0));
                }
 while(((ConstantValue)visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(1))).booleanValue()) ;
                
            }
        break;

      case 521:
{
                while(((ConstantValue)visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0))).booleanValue()) {
                	// Return the last evaluated value
                    returnSymbol = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(1));
                }

            }
        break;

      case 545:
{
            	// The assumption is that the function-type will never be changed, hence make it non-mutable.
            	FunctionType functionSignature = new FunctionType(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), false,((java.lang.String)returnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
            	
            	// If this function-type has arguments, then add them as function formal parameters
            	if(((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)) == 2) {
                    for(int argsNo = 0; argsNo < ((org.w3c.dom.NodeList)argumentsList.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength();argsNo++)
                    	functionSignature.addSymbol(visit(((org.w3c.dom.NodeList)argumentsList.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(argsNo)));                        
                }
 
            	
            	// Add the function-type in symbol table
            	// If class definition then add the symbols in current class
    			// SymbolNotDefinedException will never occur for parameters, it is just
    			// throw for constructor
    			if(Environment.isClassDefinition)
    				Environment.currentClass.addSymbol(functionSignature); 
    			else
    				Environment.currentActivationRecord.addSymbol(functionSignature);
                
                returnSymbol = functionSignature;

            }
        break;

      case 617:
{
                FunctionType function = null;
                FunctionType functionSignature = null;
                
                // If function-type is specified for this function,
                // then set the values.
                if(((java.lang.String)attributeType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) != null && ((java.lang.String)attributeType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).trim().length() > 0) {
	                try {
	                	// Extract the function-type that is specified in the @type value of the function
	                	functionSignature = (FunctionType) Environment.getSymbol(((java.lang.String)attributeType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
	                }
 catch (SymbolNotDefinedException e) { System.err.println(e.getMessage()); }

	                
	                String returnTypeIs = ((java.lang.String)returnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING));
	                // If return-type is specified again, then set the currently defined function's return type to that specified in
	                // the function definition, if not, set it to that specified in the given function-type.
	                // Here, since this function's definition will not change, make it non-mutable
	                function = new FunctionType(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), true, 
	                		(((java.lang.String)returnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) != null && ((java.lang.String)returnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).trim().length() > 0)? ((java.lang.String)returnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) : functionSignature.getReturnType());
	                
	                try {
	                // If function's return type is a 'function' then associate the appropriate function definition
	                if(Constants.FUNCTION.equals(function.getReturnType())) {
	                	if(((java.lang.String)returnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)) != null && ((java.lang.String)returnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)).trim().length() > 0)
	                		function.setReturnValue(SymbolMachine.generate(Constants.BLANK,((java.lang.String)returnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), true));
	                	else
	                		function.setReturnValue(SymbolMachine.generate(Constants.BLANK,functionSignature.getName(), true));
	                }

	                }
 catch (Exception e) { System.err.println(e.getMessage()); }

	                		
	                for(Symbol args : functionSignature.getFormalParameters())
	                	function.addSymbol(args);
	                
                }
 else {
                	// Here, since this function's definition will not change, make it non-mutable 
                	function = new FunctionType(((java.lang.String)attributeName.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), true,((java.lang.String)returnType.evaluate(node, javax.xml.xpath.XPathConstants.STRING)));
                }
  
                
                // Set the nested level information of current function
                currentNestedLevel = ((java.lang.Double)nestedLevelofFunction.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)).intValue() + 1;
                function.setNestedLevel(currentNestedLevel); 
                
                // If this function takes arguments, then even though function-type was specified for
                // this function, reset the list of formal parameters and set them according to the 
                // latest definition. 
                if(((org.w3c.dom.NodeList)formalArguments.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength() == 1/*$childrenCount > 2*/) {
                	// Clear the list of formal parameters, if any.
                	function.clearFormalParameters();
                    // Add formal parameters in function 
                    for(int argsNo = 0; argsNo < ((org.w3c.dom.NodeList)argumentsList.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).getLength();argsNo++)
                        function.addSymbol(visit(((org.w3c.dom.NodeList)argumentsList.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(argsNo)));
                }

                
                // Once the local parameters are added to the appropriate activation record's symtab,
                // restore the current nested level
                currentNestedLevel = Environment.currentActivationRecord.getNestedLevel();

                // Store the function body 
                function.setFunctionBody(((org.w3c.dom.NodeList)functionBody.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0));

                // If class definition then add the symbols in current class
    			// SymbolNotDefinedException will never occur for parameters, it is just
    			// throw for constructor
    			if(Environment.isClassDefinition)
    				Environment.currentClass.addSymbol(function);
    			else
    				Environment.currentActivationRecord.addSymbol(function);
                
                returnSymbol = function;
            }
        break;

      case 825:
{
            	String nameOfFunction = ((java.lang.String)functionName.evaluate(node, javax.xml.xpath.XPathConstants.STRING));
            	FUNCTION_TYPE functionDefinition = BuiltInFunctions.getFunctionType(nameOfFunction);
            	// initialize the list of arguments
                ArrayList<Symbol> actualArgs = new ArrayList<Symbol>(); 

            	switch(functionDefinition) {
	            	case OPERATOR :
	            		
	            		// Change the 'map' or 'reduce' keyword into actual operators
	            		if(Constants.MAP.equals(nameOfFunction))
	            			nameOfFunction = Constants.MAP_FUNCTION;
	            		else if(Constants.REDUCE.equals(nameOfFunction))
	            			nameOfFunction = Constants.REDUCE_FUNCTION;

	            		if(Constants.ARITHMETIC_OPERATORS.contains(nameOfFunction)) {
	                    	returnSymbol = processArithmeticOperator(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),nameOfFunction, true);
	                    }
  
	                    
	                    // Logical Not
	                    else if (Constants.LOGICAL_NOT.equals(nameOfFunction)) {
	                    	
	                    	returnSymbol =  processLogicalNot(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)),nameOfFunction, true);
	                    }

	                    
	                    // Conditional operator
	                    else if(Constants.CONDITIONAL_OPERATORS.contains(nameOfFunction)) {
	                    	returnSymbol = processConditionalOperator(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)), nameOfFunction, ((java.lang.String)conditionalBlock.evaluate(node, javax.xml.xpath.XPathConstants.STRING)), true);
	                    }
 

	                    // Functional operator
	                    else if(Constants.FUNCTIONAL_OPERATORS.contains(nameOfFunction)) {
	                    	returnSymbol = processFunctionalOperator(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)), nameOfFunction, true);
	                    }

	                    
	            		break;
	            	case ASSIGNMENT :

	                    Symbol lhs = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(1));
	                    Symbol rhs = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(2));
	                    try {
	                    	if(lhs == null) {
	                    		System.err.println("Trying to assign the value '" + rhs + "' to a null variable.");
	                    		return null;
	                    	}

	    					lhs.setValue(rhs);
	    				}
  catch (IllegalSymbolValueException e)  { returnSymbol = null; System.err.println(e.getMessage()); }
	
	    				catch (TypeMismatchException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

	    				catch(ImmutableSymbolException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

	                    returnSymbol = rhs; // Return rhs
	                	                    
	            		break;
	            	default :
	                	// Class-type whose function needs to be executed, if any.
	                	ClassType currentClass = null;
	                	ClassType savedCurrentClass = null;
	                	
	                	// Function to execute
	                	FunctionType function = null;
	                	
	                    
	                    // Create the list of arguments to be passed to the function
	                    for(int argsNo = 1; argsNo < ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER));argsNo++) {
	                    	
	                    	// If poly = true, then extract the class name whose function is getting called 
	                    	if(argsNo == 1 && Environment.isPolymorphism) {
	                    		currentClass = (ClassType)visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(argsNo));
	                    		savedCurrentClass = Environment.currentClass;
	                    		Environment.currentClass = currentClass;
	                    		continue;
	                    	}
 
	                        actualArgs.add(visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(argsNo)));
	                    }

	                    
	                    // If poly = true, then extract the appropriate function from the class
	                    if(Environment.isPolymorphism) {
	                    	try {
	                    		function = currentClass.lookupFunction(nameOfFunction, actualArgs);
	                    		
	                    	}
 catch(SymbolNotDefinedException e) {
	                    		System.err.println(e.getMessage());
	                    		return null; 
	                    	}

	                    }

	                    // else simply extract the function from the symbol table
	                    else {
	    	                Symbol functionSym = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0));
	    	                
	    	                // If functionSym is null, it means a call to undefined symbol has been made.
	    	                // Return null.
	    	                if(functionSym == null) {
	    	                	System.err.println("No suitable function found.");
	    	                    return null;
	    	                }

	    	                
	    	                // If var-declaration is optional, then check if constant value contains
	    	                // overloadedFunction
	    	                
	    	                if( !Environment.isVarMandatory  &&
	    	                		functionSym instanceof ConstantValue) {
	    	                		try {
	    	                			functionSym = ((ConstantValue)functionSym).overloadedFunctionValue().getFunction(actualArgs);
		    	                	}
 catch (SymbolNotDefinedException e) {
		    							System.err.println(e.getMessage());
		    							return null;
		    						}

	    	                }

	    	                else if(functionSym instanceof OverloadedFunctionType) {
	    	                    // Extract the appropriate function based on the arguments that are being passed
	    	                    try {
	    							functionSym = ((OverloadedFunctionType)functionSym).getFunction(actualArgs);
	    						}
 catch (SymbolNotDefinedException e) {
	    							System.err.println(e.getMessage());
	    							return null;
	    						}

	    	                }

	    	                // If this is a functiontype, then simply match the parameters that will be passed
	    	                // and if doesn't match, give error
	    	                else  {
	    	                	FunctionType functionSymbol = (FunctionType)functionSym;
	    	                	String formalParamSignature = functionSymbol.getSignature();
	    	                	String actualArgsSignature = functionSymbol.getSignatureOf(actualArgs);
	    	                	if(!formalParamSignature.equals(actualArgsSignature)) {
	    	                		System.err.println(new TypeMismatchException(actualArgsSignature, formalParamSignature).getMessage());
	    	            			return null;
	    	                	}

	    	                }

	    	                
	    	                // else, this is the correct function to be invoked based on arguments that are being passed
	    	                // to this function
	    	                function = (FunctionType)functionSym;
	                    }

	                         
	                    // Updated the currentNestedLevel to that of callee function.
	                    currentNestedLevel = function.getNestedLevel();
	                    
	                    // Execute the calling sequence of a caller
	                    Environment.callSequenceProcessor.callAction_caller(function.getNestedLevel());


	                     /* COMMENTED : No need since return value are not checked against return type
	                     if(Constants.FUNCTION.equals(function.getReturnType()))
	                    	 Environment.currentActivationRecord.setReturnValue(function.getReturnValue());
//	                    	 returnSymbol = function.getReturnValue(); 
	                     else
	                         // Initialize the returnValue field of activation record of current procedure.
	                    	 // Since it will be reset after function execution, make the return value as mutable
	                    	 Environment.currentActivationRecord.setReturnValue(new ConstantValue(function.getReturnType(), true));
//	                    	 returnSymbol = new ConstantValue(function.getReturnType(), true);
*/	                     
	                     try 
	                     {
	                    	// new function execution, hence enter the block
	                    	Environment.currentActivationRecord.enterTheBlock();
	                    	// Since this is a function call, actual arguments will be set inside the symbol table of a function
	                    	// Hence block has already been created, in such case, its not necessary to enter the block again when 'block'
	                    	// function comes
	                    	isAlreadyEntered = true;
	                    	
	                        // Execute the function body
	                        visit(function.execute(actualArgs));
	                        
	                        // Retrieve the stored currentClass
	                        Environment.currentClass = savedCurrentClass;
	                        
	                        // After function execution, leave the block
	                        // Environment.currentActivationRecord.leaveTheBlock();
	     
	                        /* Commented : Don't check for return value, it will be handled by front end parser
	                        // Check if function returns value other than 'void', and if yes, activation record should
	                        // have returned some value.
	                        String functionReturnType = function.getReturnType() ;
	                        if(!Constants.VOID.equals(functionReturnType) && !Environment.currentActivationRecord.isReturnsValue()) {
	                        	System.err.println("Function '" + function + "' should return a value of type '" + 
	                        			(Constants.FUNCTION.equals(functionReturnType)?function.getReturnType():functionReturnType + "'"));
	                        	returnSymbol = null;
	                        	return returnSymbol; 
	                        }
	                        // Reset that activation record has return a value.
	                        Environment.currentActivationRecord.setReturnsValue(false);
	                        */
	                        
	                        // If this is a call to constructor then return the instance of current class
	                        if(Environment.isPolymorphism && Constants.CONSTRUCTOR.equals(nameOfFunction)) {
	                        	returnSymbol = currentClass;
	                        }

	                       /* else
	                        	returnSymbol = Environment.currentActivationRecord.getReturnValue();*/
	                                            
	                     }
 catch(IllegalSymbolValueException e) { returnSymbol = null; System.err.println(e.getMessage()); }

	                     catch(TypeMismatchException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

	                     catch(SignatureMismatchException e)  { returnSymbol = null; System.err.println(e.getMessage()); }

	                    // Execute the return sequence of a caller
	                     Environment.callSequenceProcessor.returnAction_caller();
	                     currentNestedLevel = Environment.currentActivationRecord.getNestedLevel();
	                     
	                     // If poly = true, then reset the class definition flags
	                     // Variable lookup should happen in current activation record
	                     if(Environment.isPolymorphism) {
//	                    	 Environment.isClassDefinition = false;
//	                    	 Environment.currentClass = null;
	                     }

            	}

            	
            }
        break;

      case 847:
{
            	//returnSymbol = visit($children.item(0));
                // Set the return value into current activation field
                Symbol returnValue = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(0));
                try {
                    /*Environment.currentActivationRecord.getReturnValue().setValue(returnValue);
                    returnSymbol = Environment.currentActivationRecord.getReturnValue();*/
                	// Directly set the return value, without checking.
                	if(Constants.FUNCTION.equals(returnValue.getType())) {
                		returnSymbol = returnValue;
                	}
 else {
                		returnSymbol = new ConstantValue(returnValue.getType(),true);
                		returnSymbol.setValue(returnValue);
                	}

                    Environment.currentActivationRecord.setReturnsValue(true);
                }
 catch (IllegalSymbolValueException e) { System.err.println(e.getMessage()); }
 
                  catch (TypeMismatchException e) { System.err.println(e.getMessage()); }
 
                  catch(ImmutableSymbolException e) { System.err.println(e.getMessage()); returnSymbol = null; }

            }
        break;

      case 864:
{
            	if(!isAlreadyEntered)
           		 	Environment.currentActivationRecord.enterTheBlock();
	           	 // Since already entered was true, because at the time of function  call, control already entered the block,
	           	 // turn off the switch
	           	 else
	           		 isAlreadyEntered = false;
            	
                for(int childNo = 0; childNo < ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER));childNo++) {
                	// Return the last evaluated value
                    returnSymbol = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(childNo));
                }

            	Environment.currentActivationRecord.leaveTheBlock();
            }
        break;

      case 878:
{
            	Symbol symbolToPrint = null;
            	for(int stmtNo = 0; stmtNo < ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)); stmtNo++) {
                	symbolToPrint = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(stmtNo));
                	if(Constants.NO_TYPE.equals(symbolToPrint.getType())) // If no-type then throw error
                		 System.err.println("Trying to print uninitialized variable '" + symbolToPrint.getName() + "'.");
                	else
                		out.print(symbolToPrint + " ");
                }

                out.println();
            }
        break;

      case 891:
{
            	Symbol symbolToPrint = null;
                for(int stmtNo = 0; stmtNo < ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER)); stmtNo++) {
                	symbolToPrint = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(stmtNo));
                    if(Constants.NO_TYPE.equals(symbolToPrint.getType()))
                		 System.err.println("Trying to print uninitialized variable '" + symbolToPrint.getName() + "'.");
                	else
                		out.println(symbolToPrint);
                }

            }
        break;

      case 900:
{
                for(int childNo = 0; childNo < ((java.lang.Double)childrenCount.evaluate(node, javax.xml.xpath.XPathConstants.NUMBER));childNo++) {
                	// Return the last evaluated value
                    returnSymbol = visit(((org.w3c.dom.NodeList)children.evaluate(node, javax.xml.xpath.XPathConstants.NODESET)).item(childNo));
                }

            }
        break;

      default: // cannot happen
        throw xvM.error(null);
      }
    } catch (javax.xml.xpath.XPathExpressionException xvE) {
      throw xvM.error(xvE);
    }
        
        return returnSymbol;
    }
     
    private String generateTabs() {
        StringBuffer tabString = new StringBuffer("");
        for(int i = 0; i < currentNestedLevel; i++)
            tabString.append("\t");
            
        return tabString.toString();
    }
    
    /**
     * This function processes the arithmetic operations and return the result.
     * Here arguments could be more than 2
     */
    private Symbol processArithmeticOperator(NodeList operatorArgs, String operatorName, boolean isFunctionCall) {
    	ArrayList<Symbol> args = new ArrayList<Symbol>();
    	Symbol operatorResult = null;
    	int startChildNo = isFunctionCall? 1 : 0; // If function call, skip the function name i.e. operator name
    	int operatorArgsCount = operatorArgs.getLength();
        
        // read the 1st element in the list
        operatorResult = visit(operatorArgs.item(startChildNo));
        // Cummulative operation execution
        try {
        	// If unary operator
        	if(operatorArgsCount == 1) {
        		args = new ArrayList<Symbol>();
        		args.add(operatorResult);
        		operatorResult = Operator.execute(operatorName, args); // execute the operator
        	}
        	else {	
		        for(int argsNo = startChildNo + 1; argsNo < operatorArgsCount;argsNo++) {
		        	args = new ArrayList<Symbol>();
		        	args.add(operatorResult);
		        	args.add(visit(operatorArgs.item(argsNo)));
		        	// execute the operation
		        	operatorResult = Operator.execute(operatorName, args); // execute the operator
		        }
        	}
        } catch(OperatorNotFoundException e)  { System.err.println(e.getMessage()); }
        catch(IllegalSymbolValueException e) { System.err.println(e.getMessage()); }

        return operatorResult; // return the result
    }
    
    /**
     * This function processes the conditional operations and return the result.
     */
    private Symbol processConditionalOperator(NodeList operatorArgs, String operatorName, String conditionalBlockName, boolean isFunctionCall) {
    	
    	ArrayList<Symbol> args = new ArrayList<Symbol>();
    	Symbol operatorResult = null;
    	ConstantValue conditionalResult = null;
    	int operatorArgsCount = operatorArgs.getLength();
    	int startChildNo = isFunctionCall? 1 : 0; // If function call, skip the function name i.e. operator name

    	// if unary operation
    	args.add(visit(operatorArgs.item(startChildNo)));
    	// If binary operation and 2nd element is not a block
    	if(operatorArgsCount > 1 + (isFunctionCall? 1 : 0)  && !Constants.BLOCK.equals(conditionalBlockName)) 
           args.add(visit(operatorArgs.item(startChildNo + 1)));
       
    	try {
        	operatorResult = Operator.execute(operatorName, args); // execute the operator
        } catch(OperatorNotFoundException e)  { System.err.println(e.getMessage()); }
        catch(IllegalSymbolValueException e) { System.err.println(e.getMessage()); }

       
    	conditionalResult = (ConstantValue) operatorResult;

    	// If unary with 'only' condition true
    	if(operatorArgsCount == 2 + (isFunctionCall? 1 : 0) && conditionalResult.booleanValue() && Constants.BLOCK.equals(conditionalBlockName))
    		operatorResult = visit(operatorArgs.item(startChildNo + 1));
       
    	// If binary with 'if' condition true                        
    	else if(operatorArgsCount > 2 + (isFunctionCall? 1 : 0) && conditionalResult.booleanValue()) 
    		operatorResult = visit(operatorArgs.item(startChildNo + 2));
       	
    	// If binary with 'else' condition true                         
    	else if(operatorArgsCount > 3 + (isFunctionCall? 1 : 0))
    		operatorResult = visit(operatorArgs.item(startChildNo + 3));
    	
    	return operatorResult;
    }
    
    /**
     * This function processes the functional operations and return the result.
     */
    private Symbol processFunctionalOperator(NodeList operatorArgs, String operatorName , boolean isFunctionCall) {

    	int operatorArgsCount = operatorArgs.getLength();
    	Symbol operatorResult = null;
    	int startChildNo = isFunctionCall? 1 : 0; // If function call, skip the function name i.e. operator name
    	
        // Read the function name and retrieve the appropriate function body from
        // the symtab
        Symbol functionSym = visit(operatorArgs.item(startChildNo));
        
        // If functionSym is null, it means a call to undefined symbol has been made.
        // Return null.
        if(functionSym == null) {
        	System.err.println("No function found for operator '" + operatorName + "'");
            return null;
        }
        FunctionType function = null;
        // If function name is already defined and just the name is used for map/reduce
        // then get the default function as of now. So overloaded function won't work currently.
        if(functionSym instanceof OverloadedFunctionType)
        	function = ((OverloadedFunctionType) functionSym).getDefaultFunction();
        else
        	function = (FunctionType)functionSym;
        
        // Apply map function
        if(Constants.MAP_FUNCTION.equals(operatorName)) {
        	ArrayType outputOfMap = null;
        	try {
        		outputOfMap = (ArrayType)SymbolMachine.generate(Constants.BLANK, Constants.ARRAY, true);
        	} catch(IllegalSymbolValueException e){ System.err.println(e.getMessage());  }
            
            ArrayList<Symbol> actualArgs = null;
            
            // Create the list of arguments to be passed to the function
            for(int argsNo = startChildNo + 1; argsNo < operatorArgsCount;argsNo++) {
                actualArgs = new ArrayList<Symbol>();
                actualArgs.add(visit(operatorArgs.item(argsNo)));

                // Execute the calling sequence of a caller
                Environment.callSequenceProcessor.callAction_caller(function.getNestedLevel());
                     
                // Initialize the returnValue field of activation record of current procedure.
                // Since it will be reset after function execution, make the return value as mutable
//                COMMENTED : No need since return value are not checked against return type
//                Environment.currentActivationRecord.setReturnValue(new ConstantValue(function.getReturnType(), true));
//                returnSymbol = new ConstantValue(function.getReturnType(), true);
                     
                try 
                {
                	// new function execution, hence enter the block
                	Environment.currentActivationRecord.enterTheBlock();
                	// Check the comments in "call" where the function.execute() is called. 
                	isAlreadyEntered = true;
                	
                	// Execute the function body
                    // out.println("Executing function...");
                    visit(function.execute(actualArgs));
                    
                    // After function execution, leave the block
                   //Environment.currentActivationRecord.leaveTheBlock();
                    // get the return value from current activation record
                    /*//Commented : Don't check for return value, it will be handled by front end parser
                    outputOfMap.add(Environment.currentActivationRecord.getReturnValue());*/
                    outputOfMap.appendElement(returnSymbol);
                    
                    // Execute the return sequence of a caller
                    Environment.callSequenceProcessor.returnAction_caller();
                        
                } catch(IllegalSymbolValueException e) { System.err.println(e.getMessage()); /*outputOfMap.add(null);*/outputOfMap.appendElement(null); } 
                catch(TypeMismatchException e) { System.err.println(e.getMessage()); /*outputOfMap.add(null);*/outputOfMap.appendElement(null); } 
                catch(SignatureMismatchException e) { System.err.println(e.getMessage()); /*outputOfMap.add(null);*/ outputOfMap.appendElement(null);}
                operatorResult = returnSymbol;
            }
            operatorResult = outputOfMap;
        } else if(Constants.REDUCE_FUNCTION.equals(operatorName)) {
            ArrayList<Symbol> actualArgs = null;
            
            // read the 1st element in the list
            operatorResult = visit(operatorArgs.item(startChildNo + 1));
            // Create the list of arguments to be passed to the function
            for(int argsNo = startChildNo + 2; argsNo < operatorArgsCount;argsNo++) {
                actualArgs = new ArrayList<Symbol>();
                actualArgs.add(operatorResult);
                actualArgs.add(visit(operatorArgs.item(argsNo)));
         
                // Execute the calling sequence of a caller
                Environment.callSequenceProcessor.callAction_caller(function.getNestedLevel());
                 
                // Initialize the returnValue field of activation record of current procedure
                // Since it will be reset after function execution, make the return value as mutable
//                Environment.currentActivationRecord.setReturnValue(new ConstantValue(function.getReturnType(), true));
//                returnSymbol = new ConstantValue(function.getReturnType(), true);
                 
                try 
                {
                	// new function execution, hence enter the block
                	Environment.currentActivationRecord.enterTheBlock();
                	// Check the comments in "call" where the function.execute() is called. 
                	isAlreadyEntered = true;
                	
                    // Execute the function body
                    // out.println("Executing function...");
                    visit(function.execute(actualArgs));
                     
                    // After function execution, leave the block
                    // Environment.currentActivationRecord.leaveTheBlock();
                    
                    // get the return value from current activation record
                    /*// Commented : Don't check for return value, it will be handled by front end parser
                    operatorResult = Environment.currentActivationRecord.getReturnValue();*/
                    operatorResult = returnSymbol;
                    
                    // Execute the return sequence of a caller
                    Environment.callSequenceProcessor.returnAction_caller();
                    
                } catch(IllegalSymbolValueException e)  { operatorResult = null; System.err.println(e.getMessage()); }
                catch(TypeMismatchException e)  { operatorResult = null; System.err.println(e.getMessage()); }
                catch(SignatureMismatchException e)  { operatorResult = null; System.err.println(e.getMessage()); }
           } 
        }
        return operatorResult;
    }
    
    /**
     * This function processes the logical not operation and return the result.
     */
    private Symbol processLogicalNot(NodeList operatorArgs, String operatorName, boolean isFunctionCall) {
    	ArrayList<Symbol> args = new ArrayList<Symbol>();
    	int operatorArgsCount = operatorArgs.getLength();
    	Symbol operatorResult = null;
    	ConstantValue conditionalResult = null;
    	int startChildNo = isFunctionCall? 1 : 0; // If function call, skip the function name i.e. operator name
    	
    	// This is a unary operation
    	args.add(visit(operatorArgs.item(startChildNo)));
    	
    	// Execute the condition
    	try {
        	operatorResult = Operator.execute(operatorName, args); // execute the operator
        } catch(OperatorNotFoundException e)  { System.err.println(e.getMessage()); }
        catch(IllegalSymbolValueException e) { System.err.println(e.getMessage()); }
        
        conditionalResult = (ConstantValue) operatorResult;
        
        // If condition is true, then execute 'if-part'
        if(operatorArgsCount > 1 + (isFunctionCall? 1 : 0) && conditionalResult.booleanValue()) 
            operatorResult = visit(operatorArgs.item(startChildNo + 1));
        // If condition is true, then execute 'else-part'
        else if(operatorArgsCount > 2 + (isFunctionCall? 1 : 0))
            operatorResult = visit(operatorArgs.item(startChildNo + 2));
        
        return operatorResult;
    }
    
    /**
     * Main method that invokes visit() method that starts visiting root node of given
     * uXML.
     *
     */
    public static void main(String args[]) {
        Executor executor = new Executor();
        
        // get document
        Document document = null;
        String fileName = null;
        executor.out = System.out;
        try {
            if(args.length == 0) {
            	Scanner in = new Scanner(System.in);
            	System.out.print("Enter the file name @ src/sampleuXMLProgs/");
            	fileName = in.nextLine();
            }
            else {
                fileName = args[0];   
                if(args.length == 2) {
                	executor.out = new PrintStream(new FileOutputStream(new File(args[1]),true));
                }
            }
            
            fileName = "src" + File.separator + "sampleuXMLProgs" + File.separator + fileName + ".xml";
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);
            
            // remove trivial text nodes
            NodeList list = (NodeList)executor.xv.xpath.compile("//text()[not(normalize-space(.))]").evaluate(document, XPathConstants.NODESET);    
            for (int n = list.getLength(); --n >= 0; )
              list.item(n).getParentNode().removeChild(list.item(n));
        
            // remove trivial comments nodes
            list = (NodeList)executor.xv.xpath.compile("//comment()").evaluate(document, XPathConstants.NODESET);
            for (int n = list.getLength(); --n >= 0; )
              list.item(n).getParentNode().removeChild(list.item(n));
            
            // intialize the environment
            Environment.initialize(new ProcedureCallSeq(), true);
            // Make main method as the parent method of all procedures.
            executor.currentNestedLevel = 0;
            //Environment.callSequenceProcessor.callAction_caller(0);
            
            // Start visiting each node of uXML
            executor.visit(document.getDocumentElement());
        
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
		} finally {
			executor.out.println();
			executor.out.println("<-- " + fileName + " -->");
			executor.out.close();
		}
    } 
}
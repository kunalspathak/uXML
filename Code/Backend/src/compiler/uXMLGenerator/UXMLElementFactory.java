package compiler.uXMLGenerator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import compiler.components.Symbol;
import compiler.dataTypes.ConstantValue;
import compiler.util.Constants;

/**
 * The UXMLElementFactory is used to generate nodes of uXML file. It contains various methods to 
 * attach the node at various places of existing XML dom object.
 * @author kunal
 *
 */
public class UXMLElementFactory {

	/**
	 * Current UXML element 
	 
	private static UXMLElement currentNode = null;
	*/
	
	/**
	 * The uXML document that is being generated
	 */
	private static Document uXMLDocument = null;
	
	/**
	 * Root element
	 */
	private static Element root = null;
	
	/**
	 * Source code is added as comment in uXML code. commentBuf is the source code
	 * collector. 
	 */
	private static StringBuffer commentBuf = new StringBuffer("");
	
	/**
	 * @return the commentBuf
	 * Create a comment element and return. Flush the commentBuf.
	 */
	static Comment throwOut() {
		Comment comment = uXMLDocument.createComment(commentBuf.toString().replaceAll("--", "- -"));
		// Reset the buffer
		commentBuf = new StringBuffer("");
		return comment;
	}
	/**
	 * @param commentBuf the commentBuf to set
	 * This method 
	 */
	public static void eat(String sourceCode) {
		commentBuf.append(sourceCode).append(" ");
	}
	
	/**
	 * @param language - Name of source programming language 
	 * @param scope - Type of scope used in the programming language. Can be either static/dynamic
	 */
	public static UXMLElement initialize(String language, String scope) {
		try {
			uXMLDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation().createDocument(null, null, null);
		} catch (DOMException e) {
			System.err.println(e.getMessage());
		} catch (ParserConfigurationException e) {
			System.err.println(e.getMessage());
		}
		root = uXMLDocument.createElement(Constants.UXML);
		uXMLDocument.appendChild(root);
		root.setAttribute(Constants.LANGUAGE, language);
		root.setAttribute(Constants.SCOPE, scope);
		return new UXMLElement(root,null);
	}
	
	public static void done(String fileName) {
		  // use specific Xerces class to write DOM-data to a file:
	    XMLSerializer serializer = new XMLSerializer();
	    try {
	    	serializer.setOutputCharStream(new java.io.FileWriter("." + File.separator + "src" + File.separator + "sampleuXMLProgs" + File.separator + fileName + ".xml"));
	    	// Dump the document into the file.
			serializer.serialize(uXMLDocument);
			
			// Validate the document with the schema. Currently the path is hard-coded.
			SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI )
				.newSchema( new File ( ".." + File.separator + "overall.xsd" ) ).newValidator()
					.validate(new DOMSource( uXMLDocument ) );

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method generates an element node depending on the type that is passed as the argument.
	 * It also takes the list of name/value pair of attributes belonging to the element to be generated.
	 * 
	 * @param type - Type of node
	 * @param attributes - Name/Value pair of attributes
	 * @param symbol - Symbol to be set.
	 * @return - UXMLElement generated
	 */
	public static UXMLElement generateElement(String type, HashMap<String, String> attributes, Symbol symbol) {
		Element element = null;
		String name = null, value = null;
		element = uXMLDocument.createElement(type);
		if(attributes != null) {
			Iterator<String> attributeNode = attributes.keySet().iterator();
			while(attributeNode.hasNext()) {
				name = attributeNode.next();
				value = attributes.get(name);
				element.setAttribute(name, value);
			}
		}
		return new UXMLElement(element,symbol);
	}
	
	/**
	 * This method generates an element node depending on the symbol.
	 * If it is a ConstantValue, then generate "var" element, else generates
	 * "constant" element.
	 * 
	 * @param symbol - Symbol whose element is to be generated
	 * @return - UXMLElement generated
	 */
	public static UXMLElement generateID(Symbol symbol) {
		Element element = null;
		if(symbol instanceof ConstantValue) {
			element  = uXMLDocument.createElement(Constants.CONSTANT);
			element.setAttribute(Constants.VALUE, symbol.getValue());
		} else {
			element  = uXMLDocument.createElement(Constants.VAR);
			element.setAttribute(Constants.NAME, symbol.getName());
			element.setAttribute(Constants.MUTABLE, symbol.isMutable()? "yes" : "no" );
		}
		element.setAttribute(Constants.TYPE, symbol.getType());
		return new UXMLElement(element,symbol);
	}
	
	/**
	 * The method takes the UXMLElement that has symbol attribute set. It then set it's 
	 * element attribute depending on the symbol value that is already set. After that it
	 * appends the argument childElement's element attribute to that of currentNode's element.
	 * It then set childElement as currentNode.
	 * 
	 * @param childElement - uXMLElement to be appended to currentNode
	 * @return - currentNode
	 
	public static UXMLElement appendChild(UXMLElement childElement) {
//		// If childElement contains symbol then generate its equivalent element,
//		// else just proceed further
//		if(childElement.getSymbol() != null && childElement.getElement() == null)
//			childElement = generateID(childElement.getSymbol());
		
		// Append the childElement to the current Node
		currentNode.appendChild(childElement);
		
		// Make childElement as currentNode
		currentNode = childElement;
		return currentNode;
	}
	*/
	/**
	 * The method appends the childElement to the parent element  takes the UXMLElement that has symbol attribute set. It then set it's 
	 * element attribute depending on the symbol value that is already set. After that it
	 * appends the argument childElement's element attribute to that of currentNode's element.
	 * It then set childElement as currentNode.
	 * 
	 * @param childElement - uXMLElement to be appended to currentNode
	 * @return - currentNode
	 
	public static UXMLElement addChild(UXMLElement parentElement, UXMLElement childElement) {
//		// If childElement contains symbol then generate its equivalent element,
//		// else just proceed further
//		if(childElement.getSymbol() != null && childElement.getElement() == null)
//			childElement = generateID(childElement.getSymbol());
		
		// Append the childElement to the current Node
		parentElement.appendChild(childElement);
		
		// Make childElement as currentNode
		currentNode = parentElement;
		return currentNode;
	}
	*/
	/**
	 * The method takes the UXMLElement that has symbol attribute set. It then set it's 
	 * element attribute depending on the symbol value that is already set. After that it
	 * adds the argument childElement's element attribute as a sibling to that of currentNode's element.
	 * It then set childElement as currentNode.
	 * 
	 * @param siblingElement - uXMLElement to be appended to currentNode
	 * @return - currentNode
	 
	public static UXMLElement addSibling(UXMLElement siblingElement) {
		// childElement contains the symbol set. The code below will set it's element attribute 
		siblingElement = generateID(siblingElement.getSymbol());
		
		// Add the sibling to the current Node
		currentNode.addSibling(siblingElement);
		
		// Make childElement as currentNode
		currentNode = siblingElement;
		return currentNode;
	}
	
	 * The method takes the UXMLElement that has symbol attribute set. It then set it's 
	 * element attribute depending on the symbol value that is already set. After that it
	 * appends the argument childElement's element attribute to that of currentNode's element.
	 * It then set childElement as currentNode.
	 * 
	 * @param parentElement - uXMLElement to be appended to currentNode
	 * @return - currentNode
	public static UXMLElement makeParent(UXMLElement parentElement) {
		// childElement contains the symbol set. The code below will set it's element attribute 
		parentElement = generateID(parentElement.getSymbol());
		
		// Append the childElement to the current Node
		currentNode.makeParent(parentElement);
		
		// Make childElement as currentNode
		currentNode = parentElement;
		return currentNode;
	}
	*/
}

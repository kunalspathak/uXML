package compiler.uXMLGenerator;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import compiler.components.Symbol;
import compiler.util.Clone;

/**
 * This class represents a single uXML element node. It contains a node that will be 
 * used by DOM package at the time of creation of uXML in XML format. It also contain
 * a symbol that depicts what symbol the element is representing. 
 * 
 * @author kunal
 *
 */
public class UXMLElement {
	
	/**
	 * Represents the element node of the uXML - a XML file. 
	 */
	private Element element = null;
	
	/**
	 * Represents the symbol that is been referred by current element node.
	 */
	private Symbol symbol = null;
	
	/**
	 * Generate an object with either of the field value set.
	 * @param element - DOM element to be set
	 * @param symbol - symbol that is being represented by current element
	 */
	public UXMLElement(Element element, Symbol symbol) {
		this.symbol = symbol;
		
		// If symbol is received and element is null, then generate the respective 
		// element here itself
		if(symbol != null && element == null)
			this.element = UXMLElementFactory.generateID(symbol).element;
		else
			this.element = element;
	}
	/**
	 * @return the element
	 */
	public Element getElement() {
		return element;
	}
	/**
	 * @param element the element to set
	 */
	public void setElement(Element element) {
		this.element = element;
	}
	/**
	 * @return the symbol
	 */
	public Symbol getSymbol() {
		return symbol;
	}
	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * This method appends the childElement to current node.
	 * @param childElement - Child node to be appended
	 * @return
	 */
	public UXMLElement appendChild(UXMLElement childElement) {
		
		Node parentNodeOfChildElement = childElement.element.getParentNode();
		
		// If childElement is a child of some other node, then remove the association		
		if(parentNodeOfChildElement != null)
			parentNodeOfChildElement.removeChild(childElement.element);
		this.element.appendChild(childElement.element);
		return childElement;
	}
	
	/**
	 * The method appends the source code as comments inside uXML element.
	 */
	public void appendComment() {
		this.element.appendChild(UXMLElementFactory.throwOut());
	}
	
	/**
	 * The method prepends the source code as comments inside uXML element.
	 */
	public void insertComment() {
		Node parentNodeOfSiblingElement = element.getParentNode();
		parentNodeOfSiblingElement.insertBefore(UXMLElementFactory.throwOut(),element);
	}
	
	/**
	 * This method adds a sibling to current node. 
	 * @param siblingElement - The sibling node to be added
	 * @return - returns the sibling that is added in this method
	 */
	public UXMLElement addSibling(UXMLElement siblingElement) {
		Node parentNodeOfSiblingElement = siblingElement.element.getParentNode();
		
		// If sibling node is a child of some other node, then remove the association
		if(parentNodeOfSiblingElement != null)
			parentNodeOfSiblingElement.removeChild(siblingElement.element);
		parentNodeOfSiblingElement.appendChild(siblingElement.element);
		return siblingElement;
	}
	
	/**
	 * This method takes parentElement and make it as parent node 
	 * of current node.
	 * @param parentElement - The node that will become parent node of 
	 * current node.
	 * @return
	 */
	public Node makeParent(UXMLElement parentElement) {
		// Remove current node from it's parent
		Node parentNode = element.getParentNode();
		parentNode.removeChild(element);
		
		return parentElement.element.appendChild(element);
	}
	
	/**
	 * This method returns the parent element node of current element node.
	 * @return - Parent element node embedded in UXMLElement
	 */
	public UXMLElement getParent() {
		return new UXMLElement((Element) element.getParentNode(),null);
	}
	
	
	/**
	 * This method returns the element name of this node
	 * @return
	 */
	public String getElementName() {
		return element.getNodeName();
		
	}
	
	/**
	 * This method is used to extract the attribute value of an attribute present
	 * in current element
	 * @param attributeName
	 * @return
	 */
	public String getAttributeValue(String attributeName) {
		return element.getAttribute(attributeName);
	}
	
	/**
	 * This method is used to save the attribute value of an attribute present
	 * in current element
	 * @param attributeName
	 * @return
	 */
	public void setAttributeValue(String attributeName, String attributeValue) {
		element.setAttribute(attributeName, attributeValue);
	}
	
	/**
	 * The method creates the clone of existing UXMLElement 
	 * @return - Returns the deep clone
	 */
	public UXMLElement getClone() {
		UXMLElement cloned = new UXMLElement(null,null);
		cloned.setElement(element!=null? (Element)element.cloneNode(true):null);
		cloned.setSymbol(symbol!=null ? Clone.deepCopySymbol(symbol):null);
		return cloned;
	}
	
	/**
	 * This method replaces the old UXMLElement with the new UXMLElement
	 * @param newChild - New child 
	 * @param oldChild - Old child to be replaced
	 */
	public void replaceChild(UXMLElement newChild, UXMLElement oldChild) {
		element.replaceChild(newChild.element, oldChild.element);	
	}
	
	/**
	 * This method returns the first child of current element.
	 * @return
	 */
	public UXMLElement getFirstChild() {
		return new UXMLElement((Element)element.getFirstChild(),null);
	}
	
	/**
	 * The method returns the list of children UXMLElement.
	 * @return
	 */
	public ArrayList<UXMLElement> getChildren() {
		ArrayList<UXMLElement> children = new ArrayList<UXMLElement>();
		NodeList childrenNodes = element.getChildNodes();
		for(int childNo = 0; childNo < childrenNodes.getLength(); childNo++)
			children.add(new UXMLElement((Element)childrenNodes.item(childNo),null));
		return children;
	}
	
}

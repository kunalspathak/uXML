package compiler.uXMLGenerator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import compiler.util.Constants;


/**
 * @author kunal
 *
 */
public class UXMLOOCodeGenerator {

	private static Document doc = null;
	
	/**
	 * This method generates a constructor with 'formalParams' parameters. If 'formalParams'
	 * are null, then default constructor will be generated. 
	 * <constructor name="className">
	 * 	<automatic-Var>	
	 * 		<var name="varName" type="varType" mutable="varMutable"/>
	 * 		...
	 * 		...
	 * 	</automatic-Var>
	 * 	<block>
	 * 	</block>
	 * </constructor>
	 * @param className
	 * @param formalParams
	 * @return
	 */
	public static Node generateConstructor() {
		if(doc == null)
			try {
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation().createDocument(null, null, null);
			} catch (DOMException e) {
				System.err.println(e.getMessage());
			} catch (ParserConfigurationException e) {
				System.err.println(e.getMessage());
			}
	
		
		// Empty block
		Element block = doc.createElement("block");
		return block;
	}
	
	/**
	 * This method generates a call to super class of className that has been received
	 * as a parameter.
	 * <call>
	 * 	<variable name="super"/>
	 * 	<variable name=className/>
	 * </call>
	 * @param className
	 * @return
	 */
	public static Node generateCallToSuper(Document ownerDocument) {
		Element call = ownerDocument.createElement("call");
		Element variable = ownerDocument.createElement("variable");
		variable.setAttribute("name", Constants.SUPER_CLASS);
		call.appendChild(variable);
		variable = ownerDocument.createElement("variable");
		variable.setAttribute("name", "this");
		call.appendChild(variable);
		
		return call;
	}
}

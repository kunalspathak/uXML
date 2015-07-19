package compiler.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import compiler.components.Symbol;
import compiler.components.SymbolTable;


/**
 * The class makes a deep copy of object that it takes.
 * Code cited from  
 * http://www.javaworld.com/javaworld/javatips/jw-javatip76.html?page=2
 * @author kunal
 *
 */
public class Clone {
	   // so that nobody can accidentally create an ObjectCloner object
	   private Clone(){}
	   
	   // returns a deep copy of an object
	/**
	 * Takes in oldObj and make a deep clone of it and return the cloned object
	 * @param oldObj - object whose clone is to be made
	 * @return - The 'deep' cloned of onldObj 
	 * @throws Exception
	 */
	static public Symbol deepCopySymbol(Object oldObj) 
	   {
	      ObjectOutputStream oos = null;
	      ObjectInputStream ois = null;
	      Symbol copiedSymbol = null;
	      try
	      {
	         ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	         oos = new ObjectOutputStream(bos);
	         
	         // serialize and pass the object
	         oos.writeObject(oldObj);  
	         oos.flush();              
	         ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
	         ois = new ObjectInputStream(bin);
	         copiedSymbol = (Symbol)ois.readObject();
	         oos.close();
	         ois.close();
	      }
	      catch(Exception e)
	      {
	         System.out.println("Exception in ObjectCloner = " + e);
	      }
         // return the new object
         return copiedSymbol; 
	   }
	
	/**
	 * Takes in oldObj and make a deep clone of it and return the cloned object
	 * @param oldObj - object whose clone is to be made
	 * @return - The 'deep' cloned of onldObj 
	 * @throws Exception
	 */
	static public SymbolTable deepCopySymbolTable(Object oldObj) 
	   {
	      ObjectOutputStream oos = null;
	      ObjectInputStream ois = null;
	      SymbolTable copiedSymbol = null;
	      try
	      {
	         ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
	         oos = new ObjectOutputStream(bos);
	         
	         // serialize and pass the object
	         oos.writeObject(oldObj);  
	         oos.flush();              
	         ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
	         ois = new ObjectInputStream(bin);
	         copiedSymbol = (SymbolTable)ois.readObject();
	         oos.close();
	         ois.close();
	      }
	      catch(Exception e)
	      {
	         System.out.println("Exception in ObjectCloner = " + e);
	      }
         // return the new object
         return copiedSymbol; 
	   }
}

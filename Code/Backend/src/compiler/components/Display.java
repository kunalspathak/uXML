package compiler.components;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class represents the display data-structure that is used for accessing
 * non-local parameters with static-scoping.
 * @author kunal
 *
 */
public class Display {
	
	/**
	 * display represents an array of list of Activation records. Whenever a new procedure is
	 * activated, ActivationRecord of that procedure is added at the start of a list that is present
	 * at Nth position of an array, where N = nested level of a procedure.  
	 */
	private ArrayList<Stack<ActivationRecord>> displayList = null;
	
	/**
	 *  Singleton pattern.
	 */
	static private Display display = null;
	
	private Display() {
		displayList = new ArrayList<Stack<ActivationRecord>>();
	}
	
	static public Display instance() {
		if(display == null)
			display = new Display();
		return display;
	}
	
	/**
	 * This method adds the activation records in the list of activation records that is present at
	 * position 'level' of an array. 
	 * @param level - The nested-depth at which the procedure is defined. 
	 * @param activationRecord - activation record of the procedure.
	 */
	public void insertActivationRecord(int level, ActivationRecord activationRecord) {
		Stack<ActivationRecord> currentLevelRecords = null;
		// If there is no entry of activation records at this level,
		// then create a new stack at this level.
		if(level >= displayList.size()) {
			currentLevelRecords = new Stack<ActivationRecord>();
		}
		else
			currentLevelRecords = displayList.get(level);
		
		// Push the activation record that is received as a parameter into the stack.
		currentLevelRecords.push(activationRecord);

		// If there is no entry of activation records at this level,
		// then create a new stack at this level.
		if(level >= displayList.size()) {
			// If the call is deep enough, then create those many new stacks
			// This is to make sure that the stack of activation records are getting stored at the level which
			// represents the nested level of that procedure.
			for(int depth = displayList.size(); depth <= level; depth++)
				displayList.add(new Stack<ActivationRecord>());
			
//			displayList.add(currentLevelRecords);
		}
		displayList.set(level, currentLevelRecords);
	}
	
	/**
	 * This method pops the activation record that is at the top of stack at given level.	
	 * @param level
	 */
	public ActivationRecord removeActivationRecord(int level) {
		 
		try {
			ActivationRecord deActivatedRecord = displayList.get(level).pop();
			// If the stack is empty, then remove it from the display list
			// This is simply done to consume the memory.
			if(displayList.get(level).size() == 0)
				displayList.remove(level);
			return deActivatedRecord;
		} catch (EmptyStackException e) {
			return null;
		}
	}
	
	/**
	 * This method peeks the activation record that is present at the given level.
	 * @param level
	 * @return
	 */
	public ActivationRecord peekActivationRecord(int level) {
		try {
			return displayList.get(level).peek();
		} catch (EmptyStackException e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		StringBuffer stringMessage = new StringBuffer("");
		for(Stack<ActivationRecord> ar : displayList)
			stringMessage.append("[" + ar.peek() + "],\n");
		return stringMessage.toString();
	}

}

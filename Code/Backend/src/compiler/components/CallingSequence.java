package compiler.components;

import compiler.exceptions.SymbolNotDefinedException;

/**
 * This interface represents the Calling Sequence that allocates an activation
 * record and enters information in the field. 
 * Calling Sequence takes place at two different instances :
 * a. Call - This occurs when a procedure is called and just before the control is transferred 
 * to the called procedure. 
 * b. Return - This occurs just before control returns from called procedure.
 * 
 * Each of the caller and called procedure updates activation record at both of the above instances. 
 * @author kunal
 *
 */
public interface CallingSequence {
	
	/**
	 * Whenever there is a procedure call, this method is called by the caller procedure to update
	 * the activation record. The method is called just before the control is transferred to the
	 * callee procedure.
	 * 
	 * @param nestedLevel - nested level of current procedure that is getting called i.e. nested level
	 * of a callee procedure. This is required to insert the activation record in appropriate display level. 
	 */
	public void callAction_caller(int nestedLevel);
	
	/**
	 * Whenever there is a procedure call, this method is called by the callee procedure to update
	 * the activation record. The method is called just after the control reaches the callee procedure.
	 */
	public void callAction_callee();
	
	
	/**
	 * Whenever there is a return statement from current procedure, this method is called by the callee to
	 * update the activation record. The method is called just before the control returns from the callee procedure.
	 */
	public void returnAction_callee();
	
	/**
	 * Whenever there is a return statement from current procedure, this method is called by the caller to
	 * update the activation record. The method is called just after the control returns from the callee procedure.
	 *  
	 * @param nestedLevel - nested level of current procedure. This is required to remove the activation record from
	 * appropriate display level.
	 */
	public void returnAction_caller();
	
	/**
	 * This method is used to access activation record by using static scope techniques.
	 * 
	 * @param symbolName - Name of the symbol to be searched
	 * @return - Returns the symbol if found, else null
	 */
	public Symbol staticScopeAccess(String symbolName) throws SymbolNotDefinedException;
	
	/**
	 * This method is used to access activation record by using dynamic scope techniques.
	 * 
	 *  @param symbolName - Name of the symbol to be searched
	 *  @return - returns the symbol if found, else null
	 */
	public Symbol dynamicScopeAccess(String symbolName) throws SymbolNotDefinedException;
	
}

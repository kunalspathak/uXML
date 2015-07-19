package xvHandler;

import java.util.EmptyStackException;

import compiler.components.ActivationRecord;
import compiler.components.CallingSequence;
import compiler.components.Environment;
import compiler.components.Symbol;
import compiler.exceptions.SymbolNotDefinedException;


/**
 * This class takes care of calling sequence that is needed while making/return a 
 * procedure call.
 * @author kunal
 *
 */
public class ProcedureCallSeq implements CallingSequence {

	/* (non-Javadoc)
	 * @see compiler.util.CallingSequence#callAction_callee()
	 */
	@Override
	public void callAction_callee() {
		// Currently no action is taken.
	}

	/* (non-Javadoc)
	 * @see compiler.util.CallingSequence#callAction_caller()
	 */
	@Override
	public void callAction_caller(int nestedLevel) {
		
		// 1. Create a new Activation record
		ActivationRecord ar = new ActivationRecord();
		
		// 2. Set formal parameter values 
		// This action is done in client's code once callAction_caller() methods.
		// Client code will simply iterate over arguments and add them in ar's symbol
		// table.
		
		// 3. Set the nested level of current procedure in ar. This value might need in future.
		ar.setNestedLevel(nestedLevel);
		
		// 4. Set dynamic access link to current caller
		ar.setDynamicAccessLink(Environment.currentActivationRecord);
		
		// 5. Add current callee into the display
		Environment.display.insertActivationRecord(nestedLevel, ar);
		
		// 6. Make ar as currentActivationRecord
		Environment.currentActivationRecord = ar;
		
		// 7. Push currentActivationRecord on runtimeStack
		Environment.runTimeStack.push(Environment.currentActivationRecord);
		
	}

	/* (non-Javadoc)
	 * @see compiler.util.CallingSequence#returnAction_callee()
	 */
	@Override
	public void returnAction_callee() {
		// Currently no action is taken.
	}

	/* (non-Javadoc)
	 * @see compiler.util.CallingSequence#returnAction_caller()
	 */
	@Override
	public void returnAction_caller() {
		// 1. Remove current activation record from the display
		Environment.display.removeActivationRecord(Environment.currentActivationRecord.getNestedLevel());
		
		// 2. Pop the activation record from run time stack.
		try {
			Environment.runTimeStack.pop();
		} catch(EmptyStackException e) {}
		
		// 3. Make activation record that is present at top of stack as current activation record.
		Environment.currentActivationRecord = Environment.runTimeStack.peek();

	}

	@Override
	public Symbol dynamicScopeAccess(String symbolName)throws SymbolNotDefinedException {
		Symbol symbolFound = null;
		// get the current activation record
		ActivationRecord callerActivationRecord = Environment.currentActivationRecord;
		// try {
			while(callerActivationRecord != null) {
				// Check if symbol is present in this activation record
				if(callerActivationRecord.isSymbolPresent(symbolName)) {
					symbolFound = callerActivationRecord.getSymbol(symbolName);
					break;
				}
				
				// get the activation record of the caller of this procedure
				callerActivationRecord = callerActivationRecord.getDynamicAccessLink();
				
			}
		// } catch(SymbolNotDefinedException e) {return symbolFound;}
		
		return symbolFound;
	}

	@Override
	public Symbol staticScopeAccess(String symbolName) throws SymbolNotDefinedException {
		Symbol symbolFound = null;
		// get the current activation record
		ActivationRecord callerActivationRecord = Environment.currentActivationRecord;
		// try {
			while(callerActivationRecord != null) {
				if(callerActivationRecord.isSymbolPresent(symbolName)) {
					symbolFound = callerActivationRecord.getSymbol(symbolName);
					break;
				}
				// get the activation record that encloses current procedure with static-scope. 
				// Since parent procedure will be present on previous levels, reduce the level of current
				// activation record by 1 and get the most recent activation of previous level.
				
				// However, if this is the parent procedure and symbol is still not found out, then no 
				// symbol is present. Hence thrown SymbolNotDefinedException.
				if(callerActivationRecord.getNestedLevel() == 0)
					throw new SymbolNotDefinedException(symbolName);
				
				callerActivationRecord = Environment.display.peekActivationRecord(callerActivationRecord.getNestedLevel() - 1);
				
			}
		// } catch(SymbolNotDefinedException e) {return symbolFound;}
		
		return symbolFound;
	}
	
}

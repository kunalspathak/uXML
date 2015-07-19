package compiler.dataTypes;

import java.util.ArrayList;
import java.util.HashMap;

import compiler.components.Symbol;
import compiler.exceptions.IllegalSymbolValueException;
import compiler.exceptions.ImmutableSymbolException;
import compiler.exceptions.TypeMismatchException;
import compiler.util.Clone;
import compiler.util.Constants;


public class ArrayType extends Symbol  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Represents the size of an array
	 */
	private int size = 0;
	

	/**
	 * Represents the startIndex of current array
	 */
	private int startIndex = 0;
	
	/**
	 * Represents the array of individual elements that are stored in current array.
	 * Hashmap is used, since array index could be negative
	 */
	private HashMap<Integer, Symbol> value = null;

	protected ArrayType(String name, boolean mutable) {
		// Make the array-type non-mutable
		super(name, Constants.ARRAY, mutable);
	}
	
	/**
	 * This method takes the list of dimension size and optional list of start index and accordingly
	 * create the data-structure of this array. 0th element specify the size of outermost dimension,
	 * while the last number in the dimSize list specify the size of inner most dimension.
	 * Similar argument holds for start index.
	 * eg. if an array is a[5][4] then 0th entry will be '5' and 1st entry will be '4'. 
	 * @param dimSize
	 * @param dimStartIndex
	 * @param baseElement - type of array
	 * @throws IllegalSymbolValueException
	 * @throws ImmutableSymbolException 
	 * @throws TypeMismatchException 
	 */
	public void initializeArray(ArrayList<Integer> dimSize, ArrayList<Integer> dimStartIndex, Symbol baseElement) 
		throws IllegalSymbolValueException, TypeMismatchException, ImmutableSymbolException {
		
		ArrayType arrayElement = null;
		// Extract the information
		for(int dimNumber = dimSize.size() -  1; dimNumber >= 0; dimNumber--) {
			// Create the new base element
			arrayElement = (ArrayType)SymbolMachine.generate(Constants.BLANK, Constants.ARRAY, true);
			// Set its size
			arrayElement.setSize(dimSize.get(dimNumber));
			// Set the startindex, if applicable
			if(dimStartIndex != null && dimStartIndex.size() > dimNumber)
				arrayElement.setStartIndex(dimStartIndex.get(dimNumber));
			// Set the element
			arrayElement.setValue(baseElement);
			// Set back the mutable status
			arrayElement.setMutableStatus(isMutable());
			baseElement = arrayElement;
		}
		// Set the size and start index of final generated array
		setStartIndex(arrayElement.startIndex);
		setSize(arrayElement.size);
		copyArray(baseElement);
	}

	/**
	 * Returns the dimension size of this array
	 * @return the dimensionSize
	 */
	public int getDimensionSize() {
		Symbol arrayElement = this.get(startIndex);
		if(!(arrayElement instanceof ArrayType))
			return 1;
		else 
			return ((ArrayType)arrayElement).getDimensionSize() + 1;
	}
	
	/**
	 * This method returns the element-type that is stored in this array 
	 * @return - Symbol that is being stored at 0th location of the array
	 */
	public Symbol getArrayElementType() {
		Symbol arrayElement = this.get(startIndex); 
		while(true) {
			if(!(arrayElement instanceof ArrayType))
				return arrayElement;
			else
				arrayElement = ((ArrayType)arrayElement).get(startIndex);
		}
	}

	@Override
	public String getValue() {
		return toString();
	}
	
	public HashMap<Integer, Symbol> getArrayValue() {
		return value;
	}
	
	/**
	 * Returns the value stored at index 'index' of this array.
	 * @param index
	 * @return
	 */
	public Symbol get(int index) {
		if(!value.containsKey(index))
			throw new ArrayIndexOutOfBoundsException("Index " + index + " for variable '" + name + "' is out of bound.");
		return value.get(index);
	}

	/**
	 * Sets the value at index 'index' of current array
	 * @param index
	 * @param symbol
	 */
	private void set(int index, Symbol symbol) {
		if(index < startIndex || index > startIndex + size)
			throw new ArrayIndexOutOfBoundsException("Index " + index + " for variable '" + name + "' is out of bound.");
		
		if(this.value == null)
			this.value = new HashMap<Integer, Symbol>(size);
		this.value.put(index, symbol);
	}
	
	/**
	 * Copies each element of 'array' into current array's corresponding element
	 * @param array
	 */
	public void copyArray(Symbol array) { 
		if(array instanceof ArrayType) {
			ArrayType arrayToCopy = (ArrayType)array;
			Symbol arrayElement = null;
			for(int index = startIndex; index < startIndex + size; index++) {
				arrayElement = arrayToCopy.get(index);
				set(index, arrayElement instanceof PointerType ? arrayElement : (Symbol)Clone.deepCopySymbol(arrayElement));
			}
		} else if(array instanceof ConstantValue) {
			// String is a type of array
			int strIndex = 0;
			String strValue = ((ConstantValue)array).stringValue();
			CharType charValue = null; 
			for(int index = startIndex; index < startIndex + size; index++) {
				try {
					charValue = (CharType)SymbolMachine.generate(Constants.BLANK, Constants.CHAR, array.isMutable());
					charValue.setValue(strValue.charAt(strIndex++));
					set(index, charValue);
				}  
				catch (IllegalSymbolValueException e) {}
				catch (ImmutableSymbolException e) {} 
			}
		}
	}
	
	@Override
	public void setValue(Symbol symbol) throws IllegalSymbolValueException,
			TypeMismatchException, ImmutableSymbolException {
		// If symbol is another array, then make a deep copy of it 
		// and save it
		try {
		if(symbol instanceof ArrayType) {
			for(int index = startIndex; index < startIndex + size; index++)
				set(index, (Symbol)Clone.deepCopySymbol(symbol));
			symbol = null;
		} 
		// if this is a constant value, 
		else if (symbol instanceof ConstantValue) {
			switch(symbol.getDataType()) {
			case ARRAY :
				// If array, process as above
				HashMap<Integer, Symbol> arrayToSet = ((ConstantValue)symbol).arrayValue();
				for(int index = startIndex; index < startIndex + size; index++) {
					set(index, arrayToSet.get(index));
				}
				break;
				
			case STRING:
				// String is a type of array
				int strIndex = 0;
				String strValue = ((ConstantValue)symbol).stringValue();
				CharType charValue = null; 
				for(int index = startIndex; index < startIndex + size; index++) {
					charValue = (CharType)SymbolMachine.generate(Constants.BLANK, Constants.CHAR, isMutable());
					charValue.setValue(strValue.charAt(strIndex++));
					set(index, charValue);
				}
				break;
			
			case DYNAMIC:
				// Dynamic, then simply set the symbol
				for(int index = startIndex; index < startIndex + size; index++)
					set(index, (Symbol)Clone.deepCopySymbol(symbol));
				break;
			default:
				/*// If this is the 1st dimension level, then simply add the symbol into the arrays
				for(int index = startIndex; index < startIndex + size; index++) {
					set(index, (Symbol)Clone.deepCopySymbol(symbol) SymbolMachine.generate(symbol.getName(), symbol.getType(), symbol.isMutable()));
				}	*/
				throw new TypeMismatchException(symbol.getType(),Constants.ARRAY);
			}
				
		}
		// If this is the 1st dimension level, then simply add the symbol into the array
		else {
			for(int index = startIndex; index < startIndex + size; index++) {
				set(index, (Symbol)Clone.deepCopySymbol(symbol)/*SymbolMachine.generate(symbol.getName(), symbol.getType(), symbol.isMutable())*/);
			}
		}
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new IllegalSymbolValueException(e.getMessage());
		}
	}

	@Override
	public String toString() {
		StringBuilder printArray = new StringBuilder("[");
		if(size >= 1) {
			printArray.append(value.get(startIndex));
		}
		for(int index = startIndex + 1; index < startIndex + size; index++) {
				printArray.append(",")
					.append(value.get(index).toString());
		}
		
		printArray.append("]");
		return printArray.toString();
	}

	/**
	 * @return the size
	 */
	public int getArraySize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int arraySize) {
		this.size = arraySize;
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(HashMap<Integer,Symbol> value) {
		this.value = value;
	}
	
	
	/**
	 * Checks if 2 arrays have equal dimension and size
	 * @param arrayToCheck
	 * @return - 'true' if yes, else 'false'
	 */
	public boolean isEqualSize(ArrayType arrayToCheck) {
		if(size != arrayToCheck.size)
			return false;
		else {
			if(get(startIndex) instanceof ArrayType) {
				return ((ArrayType)get(startIndex)).isEqualSize((ArrayType)arrayToCheck.get(startIndex));
			}
			return true;
		}
	}
	
	/**
	 * The method appends the parameter 'symbol' to the end of current array element. 
	 * It first increase the capacity of the array by 1 and then add the element in the
	 * last position. 
	 * @param symbol - Symbol to be appended to the array.
	 */
	public void appendElement(Symbol symbol) {
		set(size++, symbol);
	}

}

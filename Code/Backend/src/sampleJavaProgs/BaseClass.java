package sampleJavaProgs;

public class BaseClass {
	
	protected String name = "a";
	
	/*public BaseClass(){
		System.out.println("Inside Base Class constructor# 1."+ this.name);
	}*/
	
	public BaseClass(int a){
		System.out.println("Inside Base Class constructor# 2.");
	}
	
	protected int number = 5;
	
	protected int getNumber() {
		return number;
	}
	
	protected void setNumber(int number) {
		this.number = number;
	}
	
	public static void main(String args[]) {
//		BaseClass b = new BaseClass();
	}
}

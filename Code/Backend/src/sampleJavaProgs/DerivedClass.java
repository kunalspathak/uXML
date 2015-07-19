package sampleJavaProgs;

public class DerivedClass extends BaseClass {

	private int num = 23;
	protected String name = "b";
	
	/*public DerivedClass(){
//		super(5);
		System.out.println("Inside Derived class constructor# 1. <-- This will be called ALWAYS!!" + this.name);
	}*/
	
	public DerivedClass(int a) {
		super(a);
		System.out.println("Inside Derived class constructor# 2. ");
		num = a;
		System.out.println("Number set : " + num);
	}
	
	public static void main(String args[]) {
		int num;
//		DerivedClass d = new DerivedClass(5);
//		DerivedClass d1 = new DerivedClass();
//		System.out.println(d1);
	}

}

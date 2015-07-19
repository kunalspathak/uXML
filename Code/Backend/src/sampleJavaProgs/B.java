package sampleJavaProgs;

public class B extends A {
	
	int i = 5;
	
	public B() {
		super(5);
		System.out.println("B()");
	}
	
	/*public B(int i) {
		super(5);
		System.out.println("B(int)");
	}*/
	
	
	void sayHi5(String name) {
		System.out.println("India B.sayHi(string)");
		System.out.println("Hello " + name + i);
		System.out.println("_______________________");
	}
	
	void changeI(int str) {
		i = str;
	}
}

package sampleJavaProgs;

import java.io.File;

public class A {
	int i = 23;
	
	/*public A() {
		System.out.println("A()");
	}*/
	
	public A(int i) {
		System.out.println("A(int)");
	}
	
	void sayHi(String name) {
		System.out.println("India A.sayHi(string)");
		System.out.println("Hello " + name + i);
		System.out.println("_______________________");
	}
	
	void sayHi(int number) {
		B b = new B();
		System.out.println("India A.sayHi(int)");
		System.out.println("Hello " + number);
		System.out.println("_______________________");
		i = 14;
		b.sayHi("five");
	}
	
	public static void main(String args[]) {
		System.out.println(File.pathSeparator);
		System.out.println(File.separator);
		/*C c = null;
		B b = new B(5);
		
		b.sayHi("World");
		//b.sayHi(5);*/
		/*A a1 = new A();
		A a2 = new A();
		a1.i = 5;
		System.out.println(a1.i + "," + a2.i);
		a1 = a2;
		a1.i = 14;
		System.out.println(a1.i + "," + a2.i);*/
	}
}

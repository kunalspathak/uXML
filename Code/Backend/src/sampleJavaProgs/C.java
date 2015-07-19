package sampleJavaProgs;

public class C /*extends A*/ {
	/*public C() {
//		super();
		System.out.println("C()");
	}*/
	
	public C(int i) {
//		super(5);
		System.out.println("C(int)");
	}
	
	public static void main(String args[]) {
		B b1 = new B();
		b1.i = 23;
		B b2 = new B();
		b1 = b2;
		/*b2.i = 320;
		System.out.println(b1.i + ":" + b2.i);
		b2 = b1;
		b2.i = 230;
		System.out.println(b1.i + ":" + b2.i);
		b1.changeI(50);
		System.out.println(b1.i + ":" + b2.i);*/
	}
}
 
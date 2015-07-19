package sampleJavaProgs;

public class DerivedDerivedClass extends DerivedClass {

	protected String name = "c";
	protected Parent parent = null;
	
	/*public DerivedDerivedClass() {
		System.out.println("Inside DerivedDerivedClass constructor# 1" + this.name);
	}*/
	
	public DerivedDerivedClass(int a) {
		super(a);
		System.out.println("Inside DerivedDerivedClass constructor# 2");
	}

	public static void main(String args[]) {
		DerivedDerivedClass d = new DerivedDerivedClass(4);
		d.parent = new Parent();
		System.out.println("Before name : " + d.parent.getParentString());
		d.changeName(d.parent);
		System.out.println("After name : " + d.parent.getParentString());
		System.out.println(d.hashCode());
	}
	
	void changeName(Parent parent) {
		parent.setParentString("Changed Name");
	}

}

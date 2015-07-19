package sampleJavaProgs;

interface Datatype {}

public class Pointers implements Datatype {

	
	private Object value = null;
	
	public Pointers() {}

	public Pointers(Object value) {
		this.value = value;
	}
	
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	public String toString() {
		return print(this);
	}
	
	public String print(Object pointer) {
		if(pointer instanceof Pointers)
			return new String("[" + ((Pointers)pointer).getValue().toString() /*print(((Pointers)pointer).getValue())*/+ "]");
		else
			return ((Int)pointer).toString();
	}
	
	
	public static void main(String args[]) {
		
		Int i,n;
		Pointers j,k,l,m;
		
		// int i = 5, n = 23
		i = new Int(5);
		n = new Int(23);
		
		// int *j, *l
		j = new Pointers(new Int());
		l = new Pointers(new Int());
		
		// int **k, **m
		k = new Pointers(new Pointers(new Int()));
		m = new Pointers(new Pointers(new Int()));
		
		// j = &i;
		j.setValue(i);
		
		// k = &j
		k.setValue(j);
		
		log(i,j,k);
		
		// i = 10;
		i.setValue(10);
		System.out.println("After i = 10");
		log(i,j,k);
		
		// n = *j
		System.out.println("n = " + n);
		n.setValue((Int)j.getValue());
		System.out.println("After ,n = *j");
		log(i,j,k); System.out.println("n = " + n);
/*		// *j = n;
		((Int)j.getValue()).setValue(n);
		System.out.println("After n = 23 , *j = n");
		log(i,j,k);
		
		// **k = n;
		n.setValue(32);
		((Int)((Pointers)k.getValue()).getValue()).setValue(n);
		System.out.println("After n = 32 , **k = n");
		log(i,j,k);*/
		
		// l = &n
		l.setValue(n);
		// m = &l
		m.setValue(l);
		
		System.out.println("After initializing n,l,m");
		log(n,l,m);
		
		// m = &j
		m.setValue(j);
		System.out.println("After m = &j");
		System.out.println("N series :");
		log(n,l,m);
		System.out.println("I series :");
		log(i,j,k);
		
		// i = 8
		System.out.println("After i = 8");
		i.setValue(8);
		log(i,j,k);
		
		// *l = 9;
		System.out.println("After *l = 9");
		((Int)l.getValue()).setValue(9);
		log(n,l,m);
	}
	
	public static void log(Int i, Pointers j, Pointers k) {
		// print i
		System.out.print("i = " + i);
		System.out.print(" , ");
		
		// print j
		System.out.print("j = " + j);
		System.out.print(" , ");
		
		// print *j
		System.out.print("*j = " + j.getValue());
		System.out.print(" , ");

		// print k
		System.out.print("k = " + k);
		System.out.print(" , ");
		
		// print *k
		System.out.print("*k = " + k.getValue());
		System.out.print(" , ");

		// print **k
		System.out.println("**k = " + ((Pointers)k.getValue()).getValue());
		System.out.println("___________________________________________________________");

	}
}


class Int implements Datatype {
	private int value = 0;
	
	public Int() {}
	
	public Int(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	public void setValue(Int newValue) {
		this.value = newValue.getValue();
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}	
	
	public String toString() {
		return String.valueOf(value);
	}
}

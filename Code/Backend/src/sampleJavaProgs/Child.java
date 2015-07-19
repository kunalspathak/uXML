package sampleJavaProgs;

class Child extends Parent
{
	String parentString = "child class";
	
    public Child() {
//        super("From Derived");
        System.out.println("Child Constructor.");
    }
    
    /*@Override
    public String getParentString() {
    	return parentString;
    }*/

    public void print()
    {
       super.print();
       System.out.println("I'm a Child Class.");
    }

    public static void main(String[] args)
    {
        Child child = new Child();
        System.out.println(child.getParentString());
//        child.print();
        /*Parent p = ((Parent)child);
        p.print();*/
//        ((Parent)child).print();
    }
}
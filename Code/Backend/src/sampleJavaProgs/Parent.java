package sampleJavaProgs;
public class Parent
{
    String parentString = "parent class";
    
    /**
	 * @return the parentString
	 */
	public String getParentString() {
		return parentString;
	}

	/**
	 * @param parentString the parentString to set
	 */
	public void setParentString(String parentString) {
		this.parentString = parentString;
	}

	public Parent()
    {
        System.out.println("Parent Constructor.");
    }

    public Parent(String myString)
    {
        parentString = myString;
        System.out.println(parentString);
    }

    public void print()
    {
       System.out.println("I'm a Parent Class.");
    }
}

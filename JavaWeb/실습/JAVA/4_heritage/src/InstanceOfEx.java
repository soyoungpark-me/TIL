class Person{
	String name;
	String id;
	
	public Person(){}
	public Person(String name){
		this.name = name;
	}
}

class Student extends Person{}
class Researcher extends Person{}
class Professor extends Person{}

public class InstanceOfEx {

	/**
	 * @param args
	 */
	static void print(Person p){
		if(p instanceof Person)
			System.out.print("Person ");
		if(p instanceof Student)
			System.out.println("Student");
		else if(p instanceof Researcher)
			System.out.println("Researcher");
		else if(p instanceof Professor)
			System.out.println("Professor");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("new Student() -> ");
		print(new Student());
		
		System.out.print("new Researcher() -> ");
		print(new Researcher());
		
		System.out.print("new Professor() -> ");
		print(new Professor());
	}
}

// 생성자 선언 및 호출 연습

public class Book {
	String title;
	String author;
	
	void show() {
		System.out.println(title + " " + author);
	}
	
	public Book(){
		this("","");
		System.out.println("생성자 호출됨.");
	}
	
	public Book(String t){
		this(t, "작자미상");
	}
	
	public Book(String t, String a){
		title = t;
		author = a;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Book javaBook = new Book("Java", "박소영");
		Book myBook = new Book("집에가고싶다");
		Book emptyBook = new Book();
		
		javaBook.show();
		myBook.show();
		emptyBook.show();
	}

}

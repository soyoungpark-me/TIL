import java.util.Scanner;

public class BookArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		String author, title;
		Book[] book = new Book[2];
		
		for(int i=0; i<book.length; i++){
			System.out.print("제목 >> ");
			author = scanner.nextLine();
			System.out.print("저자 >> ");
			title = scanner.nextLine();
			book[i] = new Book(title, author);
		}
		
		for(int i=0; i<book.length; i++)
			System.out.println("(" + book[i].title + ", " + book[i].author + ")");			
	}

}

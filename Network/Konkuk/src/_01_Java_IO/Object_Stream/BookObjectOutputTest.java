package _01_Java_IO.Object_Stream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BookObjectOutputTest {
    public static void main(String[] args) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        ArrayList list = new ArrayList();

        Book b1 = new Book("a0001", "자바완성", "홍길동", 10000);
        Book b2 = new Book("a0002", "스트럿츠", "김유신", 20000);
        Book b3 = new Book("a0003", "기초EJB", "김성박", 25000);

        list.add(b1);
        list.add(b2);
        list.add(b3);

        try {
            fout = new FileOutputStream("booklist.dat");
            oos = new ObjectOutputStream(fout);

            oos.writeObject(list);
            System.out.println("저장되었습니다.");
        } catch(Exception ex) {
        } finally {
            try {
                oos.close();
                fout.close();
            } catch (IOException ioe) {}
        }
    }
}

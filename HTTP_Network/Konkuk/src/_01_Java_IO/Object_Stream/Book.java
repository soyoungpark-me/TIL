package _01_Java_IO.Object_Stream;

import java.io.Serializable;

// Serializable을 통해 직렬화가 가능하도록 한다.
public class Book implements Serializable {
    private String isbn;
    private String title;
    transient private String author; // 필드가 마샬링 되지 않기를 원할 때 사용
    private Integer price;

    Book(String isbn, String title, String author, Integer price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String toString() {
        return getIsbn() + ", " + getTitle() + ", " + getAuthor() + ", " + getPrice();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

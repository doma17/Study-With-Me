package org.example.iterator;

public class BookMain {
    public static void main(String[] args) {
        BookShelf shelf = new BookShelf();
        shelf.addBook(new Book("Clean Code"));
        shelf.addBook(new Book("Effective Java"));

        for (Book book : shelf) {
            System.out.println("Book name : " + book.getTitle());
        }
    }
}
/*
Book name : Clean Code
Book name : Effective Java
 */
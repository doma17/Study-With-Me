package org.example.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookShelf implements Iterable<Book>{
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
    }

    //표준 Iterator 반환
    @Override
    public Iterator<Book> iterator() {
        return books.iterator();
    }
}

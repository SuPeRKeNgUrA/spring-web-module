package ru.edu.springweb.service;

import org.springframework.stereotype.Service;
import ru.edu.springweb.dao.Book;
import ru.edu.springweb.exception.BookNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private long currentId = 1;

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Книга не найдена! ID: " + id));
    }

    public Book addBook(Book book) {
        book.setId(currentId++);
        books.add(book);
        return book;
    }

    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setName(bookDetails.getName());
        book.setAuthorName(bookDetails.getAuthorName());
        return book;
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        books.remove(book);
    }
}

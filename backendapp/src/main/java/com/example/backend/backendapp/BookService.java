package com.example.backend.backendapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private Long id = 1L;
    private final List<Book> books = new ArrayList<>();

    public BookService(){
        books.add(new Book(id++, "To Kill a Mockingbird","Harper Lee",1960));
        books.add(new Book(id++, "1984","George Orwell",1949));
        books.add(new Book(id++, "The Great Gatsby", "F. Scott Fitzgerald", 1925));
        books.add(new Book(id++, "Pride and Prejudice", "Jane Austen", 1813));
        books.add(new Book(id++, "The Catcher in the Rye", "J.D. Salinger", 1951));
        books.add(new Book(id++, "Moby-Dick", "Herman Melville", 1851));
        books.add(new Book(id++, "The Lord of the Rings", "J.R.R. Tolkien", 1954));
        books.add(new Book(id++, "Brave New World", "Aldous Huxley", 1932));
        books.add(new Book(id++, "The Hobbit", "J.R.R. Tolkien", 1937));
        books.add(new Book(id++, "Frankenstein", "Mary Shelley", 1818));
    }

    public List<Book> getAllBooks(){
        return books;
    }

    public boolean addBook(Book book){
        try{
            books.add(new Book(id++, book.getTitle(),book.getAuthor(),book.getYear()));
            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }

    }

    public boolean deleteBook(Long id){
        try{
            Book temp = null;
            for(Book book: books){
                if(book.getId().equals(id)){
                    temp = book;
                    break;
                }
            }
            if(temp != null){
                books.remove(temp);
                return true;
            }
            else return false;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }

    }

    public List<Book> updateBook(Book book){
        try{
            for(int i = 0; i < books.size(); i++){
                if(books.get(i).getId().equals(book.getId())){
                    books.set(i, book);
                    return books;
                }
            }
           return null;
        } catch(Exception e){
            System.out.println(e);
            return null;
        }

    }

    
}

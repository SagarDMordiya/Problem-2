package com.example.backend.backendapp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class BookController {

    private final BookService bookService;
    private final RestTemplate restTemplate;

    @Autowired
    private BookController(BookService bookService, RestTemplate restTemplate){
        this.bookService = bookService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks(){
        try{
            List<Book> result = bookService.getAllBooks();
            if(result.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch( Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable Long id) {
        try{
            ResponseEntity<List<Book>> result = restTemplate.exchange("http://localhost:8080/books", HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {});
            List<Book> books = result.getBody();
            Optional<Book> book = books.stream().filter(b -> b.getId().equals(id)).findFirst();

            if(book.isPresent()){
                return ResponseEntity.ok(book.get());
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch( Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        try{
            if(bookService.addBook(book)) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        } catch( Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        try{
            if(bookService.deleteBook(id)) return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch( Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books")
    public ResponseEntity<List<Book>> updateBook(@RequestBody Book book) {
        try{
            List<Book> result = bookService.updateBook(book);
            if(result.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
            else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch( Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}

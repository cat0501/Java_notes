package com.itheima.controller;

import com.itheima.domain.Book;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("{id}")
    public Book get(@PathVariable Integer id){
        return bookService.getById(id);
    }

    @PostMapping
    public boolean save(@RequestBody Book book){
        return bookService.save(book);
    }

    @PutMapping
    public boolean update(@RequestBody Book book){
        return bookService.update(book);
    }

    @DeleteMapping("{id}")
    public boolean delete(@RequestBody Book book){
        return bookService.save(book);
    }

    @GetMapping
    public List<Book> getAll(){
        return bookService.getAll();
    }

}

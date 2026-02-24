package com.aizen.book.api.book.controller;



import com.aizen.book.api.book.dto.BookRequest;
import com.aizen.book.api.book.dto.BookResponse;
import com.aizen.book.api.book.model.Book;
import com.aizen.book.api.book.service.impl.BookServiceImpl;
import com.aizen.book.api.common.pagination.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookServiceImpl bookService;

    @PostMapping
    public ResponseEntity<Integer> saveBook (
            @Valid @RequestBody BookRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.save(request, connectedUser));
    }

    @GetMapping("{book_id}")
    public ResponseEntity<BookResponse> findBookById(
            @PathVariable("book_id") Integer bookId
    ){
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllBooks(page,size,connectedUser));
    }




}

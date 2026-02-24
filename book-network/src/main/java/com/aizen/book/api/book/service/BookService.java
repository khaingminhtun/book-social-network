package com.aizen.book.api.book.service;

import com.aizen.book.api.book.dto.BookRequest;
import com.aizen.book.api.book.dto.BookResponse;
import com.aizen.book.api.common.pagination.PageResponse;
import com.aizen.book.api.user.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface BookService {

    Integer save(BookRequest request, Authentication connectedUser);

    BookResponse findById(Integer bookId);



    PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser);
}

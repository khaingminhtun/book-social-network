package com.aizen.book.api.book.service;

import com.aizen.book.api.book.dto.BookRequest;
import com.aizen.book.api.book.dto.BookResponse;
import com.aizen.book.api.book.dto.BorrowedBookResponse;
import com.aizen.book.api.book.service.impl.BookServiceImpl;
import com.aizen.book.api.common.pagination.PageResponse;
import org.springframework.security.core.Authentication;

public interface BookService {

    Integer save(BookRequest request, Authentication connectedUser);

    BookResponse findById(Integer bookId);



    PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser);

    PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser);

    PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser);

    PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser);

    Integer updateShareableStatus(Integer bookId, Authentication connectedUser);

    Integer updateArchivedStatus(Integer bookId, Authentication connectedUser);

    Integer borrowBook(Integer bookId, Authentication connectedUser);

    Integer returnBorrowedBook(Integer bookId, Authentication connectedUser);

    Integer approveReturnBorrowedBook(Integer bookId, Authentication connectedUser);
}

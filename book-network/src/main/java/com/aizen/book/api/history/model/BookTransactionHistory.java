package com.aizen.book.api.history.model;

import com.aizen.book.api.book.model.Book;
import com.aizen.book.api.common.model.BaseEntity;
import com.aizen.book.api.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BookTransactionHistory extends BaseEntity {


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    // book rs

    private boolean returned;
    private boolean returnApproved;
}

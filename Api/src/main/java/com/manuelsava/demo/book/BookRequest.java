package com.manuelsava.demo.book;

import com.manuelsava.demo.book.dto.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequest {
    private BookDTO book;
    private Long universityId;
}

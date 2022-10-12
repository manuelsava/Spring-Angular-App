package com.manuelsava.demo.borrow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRequest {
    private Long studentId;
    private Long bookId;
}

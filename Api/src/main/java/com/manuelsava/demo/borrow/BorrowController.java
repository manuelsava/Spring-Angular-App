package com.manuelsava.demo.borrow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/borrows")
public class BorrowController {
    private final BorrowService borrowService;

    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping(path = "create")
    public void borrowBook(@RequestBody BorrowRequest borrowRequest) {
        borrowService.createBorrow(borrowRequest);
    }

    @DeleteMapping(path = "delete")
    public void deleteBorrow(@RequestBody BorrowRequest borrowRequest) {
        borrowService.removeBorrow(borrowRequest);
    }
}

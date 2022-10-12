package com.manuelsava.demo.borrow;

import com.manuelsava.demo.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, BorrowId> {

}

package com.manuelsava.demo.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {

    @Query("SELECT u FROM University as u WHERE u.name = ?1")
    Optional<University> findByName(String name);

    @Query("SELECT u FROM University as u WHERE u.signature = ?1")
    Optional<University> findBySignature(String signature);
}

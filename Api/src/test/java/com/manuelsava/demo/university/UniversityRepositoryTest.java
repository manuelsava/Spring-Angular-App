package com.manuelsava.demo.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(
        properties = "spring.jpa.properties.javax-persistence.validation.mode=none"
)
public class UniversityRepositoryTest {

    @Autowired
    private UniversityRepository underTest;

    @Test
    void itShouldSaveUniversity(){
        University university = new University("UNI", "UNI", "UNI");
        underTest.save(university);

        underTest.findBySignature("UNI").isPresent();
    }
}

package com.manuelsava.demo.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class UniversityServiceTest {
    UniversityService underTest;
    UniversityRepository universityRepository = mock(UniversityRepository.class);

    @Captor
    ArgumentCaptor<University> argumentCaptor = ArgumentCaptor.forClass(University.class);

    @BeforeEach
    void setUp() {
        underTest = new UniversityService(universityRepository);
    }

    @Test
    void itShouldSaveNewUniversity() {
        String name = "Università degli Studi di Milano";
        University university = new University(name, "Via Celoria", "UNIMI");

        underTest.saveUniversity(university);
        then(universityRepository).should().save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(university);
    }

    @Test
    void itShouldNotSaveNewUniversityWhenNameIsEqual() {
        String name = "Università degli Studi di Milano";
        University university = new University(name, "Via Celoria", "UNIMI");

        given(universityRepository.findByName(name)).willReturn(Optional.of(university));

        assertThatThrownBy(() -> underTest.saveUniversity(university))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("University name already exists!");
    }

    @Test
    void itShouldNotSaveNewUniversityWhenSigIsEqual() {
        String name = "Università degli Studi di Milano";
        String sig = "UNIMI";
        University university = new University(name, "Via Celoria", sig);

        given(universityRepository.findBySignature(sig)).willReturn(Optional.of(university));

        university.setName("JHH");

        assertThatThrownBy(() -> underTest.saveUniversity(university))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("University signature already exists!");
    }
}
package com.manuelsava.demo.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityService {
    private final UniversityRepository universityRepo;

    @Autowired
    public UniversityService(UniversityRepository universityRepo){
        this.universityRepo = universityRepo;
    }

    public void saveUniversity(University university) {
        //Check if name or Signature exist else throw
        if(universityRepo.findByName(university.getName()).isPresent())
            throw new IllegalStateException("University name already exists!");
        if(universityRepo.findBySignature(university.getSignature()).isPresent())
            throw new IllegalStateException("University signature already exists!");

        //save
        universityRepo.save(university);
    }

    public List<University> getUnis() {
        return universityRepo.findAll();
    }
}

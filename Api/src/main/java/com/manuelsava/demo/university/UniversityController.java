package com.manuelsava.demo.university;

import com.manuelsava.demo.university.dto.UniversityDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/university")
public class UniversityController {
    private final UniversityService universityService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    public UniversityController(UniversityService universityService){
        this.universityService = universityService;
    }

    @GetMapping(path = "all")
    public List<UniversityDTO> getAllUniversities(){
        List<University> universities = universityService.getUnis();
        return universities.stream().map(
                university -> modelMapper.map(university, UniversityDTO.class)
        ).collect(Collectors.toList());
    }

    @PostMapping(path = "create")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void addUniversity(@Valid @RequestBody UniversityDTO universityDTO){
        universityService.saveUniversity(modelMapper.map(universityDTO, University.class));
    }
}

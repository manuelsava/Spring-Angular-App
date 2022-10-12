package com.manuelsava.demo.student;

import antlr.StringUtils;
import com.manuelsava.demo.book.Book;
import com.manuelsava.demo.course_enrollment.CourseEnrollmentRepository;
import com.manuelsava.demo.enrollment.Enrollment;
import com.manuelsava.demo.enrollment.EnrollmentId;
import com.manuelsava.demo.enrollment.EnrollmentRepository;
import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final UniversityRepository universityRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, UniversityRepository universityRepository, EnrollmentRepository enrollmentRepository){
        this.studentRepository = studentRepository;
        this.universityRepository = universityRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void enrollStudent(Student student, Long uniId) {
        //1. check if university exists else throws
        Optional<University> university = universityRepository.findById(uniId);
        if(university.isEmpty())
            throw new IllegalStateException("University does not exists!");
        //generate mail address
        String mail = findValidMail(student, university.get());
        student.setEmail(mail);
        Student saved = studentRepository.save(student);
        EnrollmentId enrollmentId = new EnrollmentId(saved.getId(), uniId);
        Enrollment enrollment = new Enrollment(enrollmentId, saved, university.get());
        enrollmentRepository.save(enrollment);
    }

    private String findValidMail(Student student, University university) {
        boolean isPresent = true;
        String mail;
        int i = 0;
        do {
            mail = generateMail(student, university.getSignature(), i);
            isPresent = studentRepository.findByEmail(mail).isPresent();
            i++;
        } while (isPresent);

        return mail;
    }

    private String generateMail (Student student, String signature, int progressive){
        if(progressive == 0)
            return (student.getFirstName() + "." + student.getLastName()
                        + "@students." + signature + ".it").toLowerCase();
        return (student.getFirstName() + "." + student.getLastName()
                + progressive + "@students." + signature + ".it").toLowerCase();
    }

    public Optional<Student> getStudent(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void deleteStudent(Long studentId) {
        /*Optional<Student> student = studentRepository.findById(studentId);
        if(!student.isPresent())
            throw  new IllegalStateException("Student does not exist!");
        Student entry = student.get();
        entry.removeCourseEnrollments();
        entry.removeEnrollments();
        System.out.println(entry.getEnrollments());
        studentRepository.save(entry);*/
        studentRepository.deleteEnrollment(studentId);
        studentRepository.deleteById(studentId);
    }
}

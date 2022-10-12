package com.manuelsava.demo;

import com.manuelsava.demo.course.AddCourseRequest;
import com.manuelsava.demo.course.Course;
import com.manuelsava.demo.course.CourseService;
import com.manuelsava.demo.student.EnrollStudentRequest;
import com.manuelsava.demo.student.Student;
import com.manuelsava.demo.student.StudentRepository;
import com.manuelsava.demo.student.StudentService;
import com.manuelsava.demo.student.dto.StudentDTO;
import com.manuelsava.demo.university.University;
import com.manuelsava.demo.university.UniversityRepository;
import com.manuelsava.demo.user.UserEntity;
import com.manuelsava.demo.user.UserEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@ComponentScan
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	CommandLineRunner commandLineRunner(UniversityRepository universityRepository,
										StudentService studentService,
										CourseService courseService,
										UserEntityRepository userEntityRepository) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return args -> {
			UserEntity user = new UserEntity(
					"user1",
					encoder.encode("user1")
			);

			University milan = new University(
					"Università degli studi di Milano",
					"Via Celoria, 1",
					"UNIMI");

			University turin = new University(
					"Università degli studi di Torino",
					"Via Giuseppe Verdi, 8",
					"UNITO"
			);

			Student manuel = new Student(
					"Manuel",
					"Savà",
					LocalDate.of(1998, 4, 14));

			universityRepository.saveAll(List.of(milan, turin));

			studentService.enrollStudent(manuel, milan.getId());

			Course course = new Course("Databases", "", 6, "John Doe", 2);
			courseService.addCourse(course, 1L);

			userEntityRepository.save(user);
		};
	}
}

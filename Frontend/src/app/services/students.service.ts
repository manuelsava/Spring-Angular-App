import { HttpClient } from "@angular/common/http";
import { Injectable, OnInit, Output, EventEmitter } from "@angular/core";
import { Observable } from "rxjs";
import { Student } from "../students/student.model";
import { environment } from "src/environments/environment";

@Injectable()
export class StudentsService {
    students: Student[] = [];
    selectedStudent: Student = {} as Student;
    @Output() studentWasSelected = new EventEmitter<Student>();
    @Output() studentsLoaded = new EventEmitter();

    constructor(public http: HttpClient) {
        this.loadStudents();
    }

    loadStudents() {
        this.http.get(environment.apiUrl + "/api/v1/student/all").subscribe((res: any) => {
            this.students = [];
            res.forEach((e: any) => {
                const s = new Student(e.firstName, e.lastName, e.email, new Date(e.dateOfBirth), e.age, e.id);
                this.students.push(s);
            });

            this.studentsLoaded.emit();
        })
    }

    addStudent(student: Student, universityId: number){
        this.http.post(environment.apiUrl + "/api/v1/student/enroll", 
          {
            "student" : {
              "firstName": student.firstName,
              "lastName": student.lastName,
              "dateOfBirth": student.dob
            },
            "universityId": universityId
          }).subscribe(res => {
            if(res) {
                this.loadStudents();
            } 
        })
    }

    updateStudent(student: Student){
        this.students[student.id!] = student;
    }

    deleteStudent(studentId: number) {
        //TODO
    }

    selectStudent(student: Student) {
        this.selectedStudent = student;
        this.studentWasSelected.emit(student);
    }
}
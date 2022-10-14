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
    @Output() studentDeleted = new EventEmitter<boolean>();
    @Output() studentUpdated = new EventEmitter<boolean>();

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
                this.studentUpdated.emit(true);
            } else {
                this.studentUpdated.emit(false);
            }
        })
    }

    updateStudent(student: Student){
        this.http.post(environment.apiUrl + "/api/v1/student/edit", {
            "id": student.id,
            "firstName": student.firstName,
            "lastName": student.lastName,
            "dateOfBirth": student.dob
        }).subscribe(res => {
            if(res) {
                this.loadStudents();

            }
        })
    }

    async deleteStudent(studentId: number) {
        this.http.delete(environment.apiUrl + "/api/v1/student/delete/" + studentId).subscribe((res) => {
            if(res) {
                this.studentDeleted.emit(true);
                this.loadStudents();
            } else {
                this.studentDeleted.emit(false);
            }
        });
    }

    selectStudent(student: Student) {
        this.selectedStudent = student;
        this.studentWasSelected.emit(student);
    }

    getStudent(id: number): Student {
        var entity = {} as Student;
        this.students.forEach((s) => {
            if(s.id === id)
                entity = s;
        });

        return entity;
    }

    getStudents() {
        return this.students;
    }
}
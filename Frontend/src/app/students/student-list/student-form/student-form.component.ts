import { HttpClient, HttpResponse } from '@angular/common/http';
import { Component, ElementRef, EventEmitter, OnInit, Input, Output, ViewChild } from '@angular/core';
import { Student } from '../../student.model';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { University } from '../../university.model';
import { StudentsService } from 'src/app/services/students.service';

@Component({
  selector: 'app-student-form',
  templateUrl: './student-form.component.html',
  styleUrls: ['./student-form.component.css']
})
export class StudentFormComponent implements OnInit {
  @Input() universities: University[] = [];
  @ViewChild('firstNameInput') firstNameInputRef!: ElementRef;
  @ViewChild('lastNameInput') lastNameInputRef!: ElementRef;
  @ViewChild('emailInput') emailInputRef!: ElementRef;
  @ViewChild('dobInput') dobInputRef!: ElementRef;
  @ViewChild('uniInput') uniInputRef!: ElementRef;

  constructor(private http: HttpClient, private sutdentsService: StudentsService) {}

  ngOnInit(): void {
    this.loadUniversities().subscribe(this.parseUniversities)
  }

  onAddStudent() {
    const stFirstName = this.firstNameInputRef.nativeElement.value;
    const stLastName = this.lastNameInputRef.nativeElement.value;
    //const stEmail = this.emailInputRef.nativeElement.value;
    const stDob = this.dobInputRef.nativeElement.value;
    const stAge = 2022 - stDob.split("-")[0];
    const uniId = this.uniInputRef.nativeElement.value;
    const newStudent = new Student(stFirstName, stLastName, undefined, stDob, stAge);
    this.sutdentsService.addStudent(newStudent, uniId);
  }

  loadUniversities(): Observable<Object> {
    return this.http.get(environment.apiUrl + "/api/v1/university/all");
  }

  parseUniversities = (res: any) => {
    res.forEach((e: any) => {
      const u = new University(e.id, e.name, e.signature, e.address);
      this.universities.push(u);
    });
  }
}

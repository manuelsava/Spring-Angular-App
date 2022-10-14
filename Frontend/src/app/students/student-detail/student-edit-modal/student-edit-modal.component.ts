import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { StudentsService } from 'src/app/services/students.service';
import Swal from 'sweetalert2';
import { Student } from '../../student.model';

@Component({
  selector: 'app-student-edit-modal',
  templateUrl: './student-edit-modal.component.html',
  styleUrls: ['./student-edit-modal.component.css']
})
export class StudentEditModalComponent implements OnInit {
  @Input() student!: Student;
  @ViewChild('firstNameInput') firstNameRef!: ElementRef;
  @ViewChild('lastNameInput') lastNameInputRef!: ElementRef;
  @ViewChild('emailInput') emailInputRef!: ElementRef;
  @ViewChild('dobInput') dobInputRef!: ElementRef;

  closeResult: string = '';

  constructor(private modalService: NgbModal, private studentService: StudentsService, private http: HttpClient) { }

  ngOnInit(): void {
    this.studentService.studentUpdated.subscribe((res) => {
      if(res) {
        Swal.fire(
          'Updated!',
          'Student updated correctly!',
          'success'
        )
      } else {
        Swal.fire(
          'Error!',
          'Could not update student!',
          'error'
        )
      }
    })
  }

  onAddStudent() {
    var newStudent = {} as Student;
    /*const firstName = this.firstNameRef.nativeElement.value;
    const lastName = this.lastNameInputRef.nativeElement.value;
    const dob = this.dobInputRef.nativeElement.value;*/

    

    /*newStudent.firstName = firstName;
    newStudent.lastName = lastName;
    newStudent.dob = dob;*/

    this.studentService.updateStudent(newStudent);
  }

  open(content: any) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  parseDob(): string {
    return this.student.dob?.getFullYear() + "-" + this.student.dob?.getMonth() + "-" + this.student.dob?.getDay();
  }
}

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';

import { AppComponent } from './app.component';
import { StudentsComponent } from './students/students.component';
import { StudentListComponent } from './students/student-list/student-list.component';
import { StudentDetailComponent } from './students/student-detail/student-detail.component';
import { HeaderComponent } from './header/header.component';
import { StudentItemComponent } from './students/student-list/student-item/student-item.component';
import { FormsModule } from '@angular/forms';
import { StudentFormComponent } from './students/student-list/student-form/student-form.component';
import { DropdownDirective } from './shared/dropdown.directive';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { StudentsPageComponent } from './students/students-page/students-page.component';
import { RequestInterceptor } from './request.interceptor';
import { StudentsService } from './services/students.service';
import { NotFoundComponent } from './not-found/not-found.component';
import { StudentResolver } from './students/student-resolver-service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { StudentEditModalComponent } from './students/student-detail/student-edit-modal/student-edit-modal.component';
import { StudentEditFormComponent } from './students/student-detail/student-edit-form/student-edit-form.component';
import { CourseComponent } from './course/course.component';
import { CourseListComponent } from './course/course-list/course-list.component';
import { CoursesService } from './services/courses.service';
import { CourseDetailComponent } from './course/course-detail/course-detail.component';
import { CourseItemComponent } from './course/course-list/course-item/course-item.component';

@NgModule({
  declarations: [
    AppComponent,
    StudentsComponent,
    StudentListComponent,
    StudentDetailComponent,
    HeaderComponent,
    StudentItemComponent,
    StudentFormComponent,
    DropdownDirective,
    LoginComponent,
    StudentsPageComponent,
    NotFoundComponent,
    StudentEditModalComponent,
    StudentEditFormComponent,
    CourseComponent,
    CourseListComponent,
    CourseDetailComponent,
    CourseItemComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModule
  ],
  providers: [{ 
    provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true}, 
    StudentsService, 
    StudentResolver, 
    CoursesService],
  bootstrap: [AppComponent]
})
export class AppModule { }

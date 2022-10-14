import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from './authentication.guard';
import { CourseComponent } from './course/course.component';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { StudentDetailComponent } from './students/student-detail/student-detail.component';
import { StudentResolver } from './students/student-resolver-service';
import { Student } from './students/student.model';
import { StudentsPageComponent } from './students/students-page/students-page.component';

const routes: Routes = [
  { 
    path: '', canActivate: [AuthenticationGuard], children: [
      { path: 'login', component: LoginComponent },
      { path: 'students', 
        component: StudentsPageComponent, 
        children: [
          { path: ':id', component: StudentDetailComponent, resolve: { student: StudentResolver } },
          //{ path: ':id/edit', component:  }
      ] },
      { path: 'courses',
        component: CourseComponent,
        children: [
          
        ]
      },
      { path: 'not-found', component: NotFoundComponent },
      { path: '**', redirectTo: '/not-found' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

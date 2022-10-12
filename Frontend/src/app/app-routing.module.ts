import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticationGuard } from './authentication.guard';
import { LoginComponent } from './login/login.component';
import { Student } from './students/student.model';
import { StudentsPageComponent } from './students/students-page/students-page.component';

const routes: Routes = [
  { 
    path: '', canActivate: [AuthenticationGuard], children: [
      { path: 'login', component: LoginComponent },
      { path: 'students', component: StudentsPageComponent },
      //{ path: '**', redirectTo: '' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

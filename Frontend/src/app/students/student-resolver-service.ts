import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, Resolve, Route, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs";
import { StudentsService } from "../services/students.service";
import { Student } from "./student.model";

@Injectable()
export class StudentResolver implements Resolve<Student> {
    constructor(private studentsService: StudentsService){}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Student | Observable<Student> | Promise<Student> {
        console.log("resolving");
        return this.studentsService.getStudent(+route.params['id']);
    }
}
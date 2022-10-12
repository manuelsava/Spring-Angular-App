import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  sessionId: any = "";

  @ViewChild('usernameInput') usernameInputRef!: ElementRef;
  @ViewChild('passwordInput') passwordInputRef!: ElementRef;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }

  onLogin() {
    const username = this.usernameInputRef.nativeElement.value;
    const password = this.usernameInputRef.nativeElement.value;

    this.http.post(environment.apiUrl + "/api/v1/login/", 
      {
        "username": username,
        "password": password
      }).subscribe(res => {
        if(res) {
          // eslint-disable-next-line @typescript-eslint/ban-ts-comment
          // @ts-ignore
          this.sessionId = res.sessionId;
          
          alert("Authenticated! Session id: " + this.sessionId);

          sessionStorage.setItem(
            'token', 
            this.sessionId
          )

          this.router.navigate(['/students']);
        } else {
          alert("Authentication failed!");
        }
      });
  }
}

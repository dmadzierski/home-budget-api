import {Component, OnInit} from '@angular/core';
import {User} from '../models/user.model';
import {RegisterHttpService} from './register.http.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  private registerHttpService: RegisterHttpService;
  user: User = new User();

  emailErrors: Array<string> = new Array<string>();
  passwordErrors: Array<string> = new Array<string>();

  constructor(registerHttpService: RegisterHttpService) {
    this.registerHttpService = registerHttpService;
  }

  ngOnInit(): void {
  }


  registerUser() {
    this.registerHttpService.registerUser(this.user).subscribe(post => {
      this.registerHttpService.getRouter().navigate(['/login']);
    }, error => {
      this.emailErrors = error.error.errors.email;
      this.passwordErrors = error.error.errors.password;
    });
  }
}

import {Component, OnInit} from '@angular/core';
import {RegisterHttpService} from './register.http.service';
import {Router} from '@angular/router';
import {User} from '../models/user/user.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  emailErrors: Array<string> = new Array<string>();
  passwordErrors: Array<string> = new Array<string>();

  constructor(private registerHttpService: RegisterHttpService, private router: Router) {
  }

  ngOnInit(): void {
  }


  registerUser() {
    this.registerHttpService.registerUser(this.user).subscribe(post => {
      this.router.navigate(['/login']);
    }, error => {
      console.log(error);
      this.emailErrors = error.error.errors.email;
      this.passwordErrors = error.error.errors.password;
    });
  }
}

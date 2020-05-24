import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  email: string = this.auth.getUserName();

  constructor(private auth: AuthService) {
    auth.authenticate(undefined, undefined);
  }

  isAuth() {
    return this.auth.isAuthenticated();
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.auth.logout();
  }

}

import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  constructor(private auth: AuthService) {
    auth.authenticate(undefined, undefined);
  }

  ngOnInit(): void {
  }

  authenticated() {
    return this.auth.authenticated;
  }
}

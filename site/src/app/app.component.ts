import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from './auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'Home budget';
  private protectedRoutes: Array<string> = [
    '/wallet',
    '/category'
  ];

  constructor(private router: Router, private authService: AuthService, private activatedRoute: ActivatedRoute) {
  }

  userHasAccess() {
    const routerUrl = this.router.routerState.snapshot.url;
    if (this.isProtectedRouter(routerUrl) && !this.authService.isAuthenticated()) {
      this.router.navigateByUrl('/');
    } else {
      return true;
    }
  }

  private isProtectedRouter(routerUrl: string): boolean {
    return this.protectedRoutes.some(k => routerUrl.match(k));
  }
}

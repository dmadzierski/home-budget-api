import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RouterModule, Routes} from '@angular/router';
import {RegisterComponent} from './register/register.component';
import {NavigationComponent} from './navigation/navigation.component';
import {FooterComponent} from './footer/footer.component';
import {HomeComponent} from './home/home.component';

const routes: Routes = [
  // {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NavigationComponent,
    FooterComponent,
    HomeComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}

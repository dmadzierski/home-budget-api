import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RouterModule, Routes} from '@angular/router';
import {RegisterComponent} from './register/register.component';
import {NavigationComponent} from './navigation/navigation.component';
import {FooterComponent} from './footer/footer.component';
import {HomeComponent} from './home/home.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RegisterHttpService} from './register/register.http.service';
import {WalletComponent} from './wallet/wallet.component';
import {WalletCreatorComponent} from './wallet/wallet-creator/wallet-creator.component';
import {WalletDetailsComponent} from './wallet/wallet-details/wallet-details.component';
import {ErrorComponent} from './error/error.component';
import {TransactionCreatorComponent} from './transaction/transaction-creator/transaction-creator.component';
import {TransactionComponent} from './transaction/transaction.component';
import {CategoryComponent} from './category/category.component';
import {CategoryCreatorComponent} from './category/category-creator/category-creator.component';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'wallet', component: WalletComponent},
  {path: 'wallet/add', component: WalletCreatorComponent},
  {path: 'wallet/details', component: WalletDetailsComponent},
  {path: 'category', component: CategoryComponent},
  {path: 'category/add', component: CategoryCreatorComponent}
];


@Injectable()
export class XhrInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler) {
    if (localStorage.getItem('email') && localStorage.getItem('basicauth')) {
      req = req.clone({
        setHeaders: {
          Authorization: localStorage.getItem('basicauth')
        }
      });
    }
    req = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(req);
  }
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NavigationComponent,
    FooterComponent,
    HomeComponent,
    WalletComponent,
    WalletCreatorComponent,
    WalletDetailsComponent,
    ErrorComponent,
    TransactionCreatorComponent,
    TransactionComponent,
    CategoryComponent,
    CategoryCreatorComponent,
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    RegisterHttpService,
    {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}


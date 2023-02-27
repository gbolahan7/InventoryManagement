import {ModuleWithProviders, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpRequest} from '@angular/common/http';

import {
  NbAlertModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbIconModule,
  NbInputModule,
  NbLayoutModule,
} from '@nebular/theme';

import { TokenLocalStorage, TokenStorage } from './token/token-storage';
import { TokenService } from './token/token.service';
import { AuthTokenWrapper } from './token/token-wrapper';
import { AuthComponent } from './component/auth.component';
import { AuthBlockComponent } from './auth-block/auth-block.component';
import { LoginComponent } from './component/login/login.component';
import {AUTH_TOKEN_INTERCEPTOR_FILTER} from "./auth.options";
import {AuthJWTInterceptor} from "./service/auth-interceptor";
import {AuthService} from "./service/auth.service";
import {AuthGuard} from "./service/auth-guard.services";
import {RegisterComponent} from "./component/register/register.component";
import {LogoutComponent} from "./component/logout/logout.component";

export function filterInterceptorRequest(req: HttpRequest<any>) {
  return ['http://localhost:8099/api/auth/',].some(url => req.url.includes(url));
}

@NgModule({
  imports: [
    CommonModule,
    NbLayoutModule,
    NbCardModule,
    NbCheckboxModule,
    NbAlertModule,
    NbInputModule,
    NbButtonModule,
    RouterModule,
    FormsModule,
    NbIconModule,
  ],
  declarations: [
    AuthComponent,
    AuthBlockComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent
  ],
  exports: [
    AuthComponent,
    AuthBlockComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent
  ],
})

export class AuthModule {
  static forRoot(): ModuleWithProviders<AuthModule> {
    return {
      ngModule: AuthModule,
      providers: [
        { provide: TokenStorage, useClass: TokenLocalStorage },
        { provide: AUTH_TOKEN_INTERCEPTOR_FILTER, useValue: filterInterceptorRequest },
        { provide: HTTP_INTERCEPTORS, useClass: AuthJWTInterceptor, multi: true },
        AuthTokenWrapper,
        TokenService,
        AuthService,
        AuthGuard
      ],
    };
  }
}

import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../../service/auth.service";
import {AuthResponse} from "../../model/auth-response";

@Component({
  selector: 'register',
  styleUrls: ['./register.component.scss'],
  templateUrl: './register.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class RegisterComponent {

  redirectDelay: number = 0;
  showMessages: any = {};
  strategy: string = '';

  submitted = false;
  errors: string[] = [];
  messages: string[] = [];
  user: any = {};

  constructor(protected service: AuthService,
              protected cd: ChangeDetectorRef,
              protected router: Router) {

  }

  register(): void {
    this.errors = this.messages = [];
    this.submitted = true;

    this.service.register(this.user).subscribe((response: AuthResponse) => {
      this.submitted = false;
      if (response.isSuccess()) {
        this.messages = response.getMessages();
      } else {
        this.errors = response.getErrors();
      }
      const redirect = response.getRedirect();
      if (redirect) {
        setTimeout(() => {
          return this.router.navigateByUrl(redirect);
        }, this.redirectDelay);
      }
      this.cd.detectChanges();
    });
  }

}

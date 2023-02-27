import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../../service/auth.service";
import {AuthResponse} from "../../model/auth-response";

@Component({
  selector: 'logout',
  templateUrl: './logout.component.html',
})
export class LogoutComponent implements OnInit {

  redirectDelay: number = 0;

  constructor(protected service: AuthService,
              protected router: Router) {
  }

  ngOnInit(): void {
    this.logout();
  }

  logout(): void {
    this.service.logout().subscribe((result: AuthResponse) => {
      const redirect = result.getRedirect();
      if (redirect) {
        setTimeout(() => {
          return this.router.navigateByUrl(redirect);
        }, this.redirectDelay);
      }
    });
  }

}

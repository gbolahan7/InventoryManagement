import {NbRoleProvider} from "@nebular/security";
import {Observable, of as observableOf} from "rxjs";
import {Injectable} from "@angular/core";
import {AuthService} from "../../pages/auth/service/auth.service";
import {map} from "rxjs/operators";

@Injectable()
export class SimpleRoleProvider extends NbRoleProvider {

  private authService: AuthService;
  constructor(private auth: AuthService) {
    super();
    this.authService = auth;
  }

  getRole(): Observable<string[]> {
    return this.authService.getUser().pipe(map(value => value.permissions))
  }
}

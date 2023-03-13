import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {filter, map, mergeMap, tap} from 'rxjs/operators';
import {AuthService} from "./auth.service";
import {NbAccessChecker} from "@nebular/security";
import {forkJoin, Observable, of} from "rxjs";

@Injectable()
export class RoleGuard implements CanActivate {

  constructor(private accessChecker: NbAccessChecker, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    let roleData: {key: number, value: {permission: string, resource: string}} = route.data as {key: number, value: {permission: string, resource: string}};
    if(!roleData) return of(true);
    let result: {permission: string, resource: string}[] = Object.keys(roleData).map((key) => roleData[key]);
    if(result.length == 0) return of(true);
    return of(result).pipe(
      mergeMap((q: {permission: string, resource: string}[]) => forkJoin(
        q.map(roleData => this.accessChecker.isGranted(roleData.permission, roleData.resource))
      )),
      map((result: [boolean]) => {
        let checker = (arr: [boolean] ) => arr.every(v => v === false);
        if(checker(result)) {
          this.router.navigate(['pages/no-access']);
          return false;
        }
        return true;
      })
    );

  }
}

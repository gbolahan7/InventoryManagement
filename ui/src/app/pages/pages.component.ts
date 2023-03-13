import { Component } from '@angular/core';

import {NbMenuItem} from "@nebular/theme";
import {map, mergeMap, take} from "rxjs/operators";
import {BehaviorSubject, forkJoin, of} from "rxjs";
import {NbAccessChecker} from "@nebular/security";

@Component({
  selector: 'ngx-pages',
  styleUrls: ['./pages.component.scss'],
  templateUrl: './pages.component.html',
})
export class PagesComponent {
  menuItem: NbMenuItem[] = null;

  constructor(private accessChecker: NbAccessChecker) {
    this.loadMenuItems();
  }

  private loadMenuItems(): void {
    this.menuItem = [
      {
        title: 'Inventory',
        icon: 'shopping-cart-outline',
        hidden: !this.isEnabledList([
          {permission: 'category', resource: 'view'},
          {permission: 'category', resource: 'create'},
        ]),
        children: [
          {
            title: 'Category',
            hidden: !this.isEnabledList([
              {permission: 'category', resource: 'view'},
              {permission: 'category', resource: 'create'},
            ]),
            children: [
              {
                title: 'Home',
                link: '/pages/inventory/category',
                icon: 'home-outline',
                hidden: !this.isEnabled('category', 'view'),
              },
              {
                title: 'Create',
                link: '/pages/inventory/category/create',
                icon: 'plus-circle-outline',
                hidden: !this.isEnabled('category', 'create'),
              },
              {
                title: 'List',
                link: '/pages/inventory/category/list',
                icon: 'list-outline',
                hidden: !this.isEnabled('category', 'view'),
              },
              {
                title: 'Request',
                link: '/pages/inventory/category/request',
                icon: 'archive-outline',
                hidden: !this.isEnabled('category', 'view'),
              },

            ]
          },
        ],
      },
      {
        title: 'Admin',
        icon: 'people-outline',
        hidden: !this.isEnabledList([
          {permission: 'role', resource: 'operation'},
          {permission: 'user', resource: 'operation'},
        ]),
        children: [
          {
            title: 'Role',
            hidden: !this.isEnabled('role', 'operation'),
            children: [
              {
                title: 'Operation',
                link: '/pages/admin/role/list',
                icon: 'radio-button-on-outline',
                hidden: !this.isEnabled('role', 'operation'),
              },
            ]
          },
          {
            title: 'User',
            hidden: !this.isEnabled('user', 'operation'),
            children: [
              {
                title: 'Operation',
                link: '/pages/admin/user/list',
                icon: 'person-done-outline',
                hidden: !this.isEnabled('user', 'operation'),
              },
            ]
          },
        ],
      },
      {
        title: 'Miscellaneous',
        icon: 'shuffle-2-outline',
        hidden: !this.isEnabled('product', 'add'),
        children: [
          {
            title: '404',
            link: '/pages/miscellaneous/404',
            hidden: !this.isEnabled('product', 'view'),
          },
        ],
      },
    ];
  }

  private  isEnabled(permission: string, resource: string): boolean {
  let isHiddenSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    this.accessChecker.isGranted(permission, resource)
      .pipe( take(1) )
      .subscribe(value => {
        isHiddenSubject = new BehaviorSubject<boolean>(value);
      });
    setTimeout(function(){}, 1000);
    return isHiddenSubject.getValue();
  }

  private  isEnabledList(value: {permission: string, resource: string}[]): boolean {
    let isHiddenSubject: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
    of(value).pipe(
      mergeMap((q: {permission: string, resource: string}[]) => forkJoin(
        q.map(roleData => this.accessChecker.isGranted(roleData.permission, roleData.resource))
      ))
    ).subscribe((result: [boolean]) => {
      let checker = (arr: [boolean] ) => arr.every(v => v === false);
      if(checker(result)) {
        isHiddenSubject = new BehaviorSubject<boolean>(false);
      }else{
        isHiddenSubject = new BehaviorSubject<boolean>(true);
      }
    });
    setTimeout(function(){}, 1000);
    return isHiddenSubject.getValue();
  }

}

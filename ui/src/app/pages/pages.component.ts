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
        title: 'Dashboard',
        hidden: !this.isEnabled('chart', 'view'),
        icon: 'home-outline',
        home: true,
        link: '/pages/dashboard'
      },
      {
        title: 'Inventory',
        icon: 'shopping-bag-outline',
        hidden: !this.isEnabledList([
          {permission: 'category', resource: 'view'},
          {permission: 'purchase_order', resource: 'view'},
          {permission: 'product', resource: 'view'},
          {permission: 'unit', resource: 'view'},
        ]),
        children: [
          {
            title: 'Product',
            icon: 'archive-outline',
            hidden: !this.isEnabledList([
              {permission: 'product', resource: 'view'},
              {permission: 'product', resource: 'create'},
            ]),
            children: [
              {
                title: 'Home',
                link: '/pages/inventory/product',
                icon: 'home-outline',
                hidden: !this.isEnabled('product', 'view'),
              },
              {
                title: 'Create',
                link: '/pages/inventory/product/create',
                icon: 'plus-circle-outline',
                hidden: !this.isEnabled('product', 'create'),
              },
              {
                title: 'List',
                link: '/pages/inventory/product/list',
                icon: 'list-outline',
                hidden: !this.isEnabled('product', 'view'),
              },
              {
                title: 'Request',
                link: '/pages/inventory/product/request',
                icon: 'archive-outline',
                hidden: !this.isEnabled('product', 'view'),
              },

            ]
          },
          {
            title: 'Purchase Order',
            icon: 'shopping-cart-outline',
            hidden: !this.isEnabledList([
              {permission: 'purchase_order', resource: 'view'},
              {permission: 'purchase_order', resource: 'create'},
            ]),
            children: [
              {
                title: 'Home',
                link: '/pages/inventory/purchase-order',
                icon: 'home-outline',
                hidden: !this.isEnabled('purchase_order', 'view'),
              },
              {
                title: 'Create',
                link: '/pages/inventory/purchase-order/create',
                icon: 'plus-circle-outline',
                hidden: !this.isEnabled('purchase_order', 'create'),
              },
              {
                title: 'List',
                link: '/pages/inventory/purchase-order/list',
                icon: 'list-outline',
                hidden: !this.isEnabled('purchase_order', 'view'),
              },
              {
                title: 'Request',
                link: '/pages/inventory/purchase-order/request',
                icon: 'archive-outline',
                hidden: !this.isEnabled('purchase_order', 'view'),
              },

            ]
          },
          {
            title: 'Category',
            icon: 'shield-outline',
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
          {
            title: 'Unit',
            icon: 'funnel-outline',
            hidden: !this.isEnabledList([
              {permission: 'unit', resource: 'view'},
              {permission: 'unit', resource: 'create'},
            ]),
            children: [
              {
                title: 'Home',
                link: '/pages/inventory/unit',
                icon: 'home-outline',
                hidden: !this.isEnabled('unit', 'view'),
              },
              {
                title: 'Create',
                link: '/pages/inventory/unit/create',
                icon: 'plus-circle-outline',
                hidden: !this.isEnabled('unit', 'create'),
              },
              {
                title: 'List',
                link: '/pages/inventory/unit/list',
                icon: 'list-outline',
                hidden: !this.isEnabled('unit', 'view'),
              },
              {
                title: 'Request',
                link: '/pages/inventory/unit/request',
                icon: 'archive-outline',
                hidden: !this.isEnabled('unit', 'view'),
              },

            ]
          },
        ],
      },
      {
        title: 'Performance',
        icon: 'trending-up-outline',
        hidden: !this.isEnabledList([
          {permission: 'setting', resource: 'view'},
          {permission: 'staff', resource: 'view'},
        ]),
        children: [
          {
            title: 'Staff',
            icon: 'people-outline',
            link: '/pages/performance/staff',
            hidden: !this.isEnabledList([
              {permission: 'staff', resource: 'view'},
            ]),
          },
          {
            title: 'Setting',
            icon: 'settings-2-outline',
            hidden: !this.isEnabledList([
              {permission: 'setting', resource: 'view'},
              {permission: 'setting', resource: 'modify'},
            ]),
            children: [
              {
                title: 'View Setting',
                link: '/pages/performance/setting/list/1',
                icon: 'settings-outline',
                hidden: !this.isEnabled('setting', 'view'),
              },
              {
                title: 'Information',
                link: '/pages/performance/setting/request',
                icon: 'archive-outline',
                hidden: !this.isEnabled('setting', 'view'),
              },
            ]
          },
        ],
      },
      {
        title: 'Report',
        icon: 'email-outline',
        hidden: !this.isEnabledList([
          {permission: 'report', resource: 'view'},
        ]),
        children: [
          {
            title: 'Unit',
            link: '/pages/report/unit',
          },
          {
            title: 'Product',
            link: '/pages/report/product',
          },
          {
            title: 'Category',
            link: '/pages/report/category',
          },
          {
            title: 'Purchase Order',
            link: '/pages/report/purchase-order',
          },
          {
            title: 'Purchase Items',
            link: '/pages/report/purchase-order-item',
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

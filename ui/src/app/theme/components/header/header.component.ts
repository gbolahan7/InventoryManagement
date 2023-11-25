import { Component, OnDestroy, OnInit } from '@angular/core';
import { NbMediaBreakpointsService, NbMenuService, NbSidebarService, NbThemeService } from '@nebular/theme';

import { LayoutService } from '../../../core/utils';
import { map, takeUntil } from 'rxjs/operators';
import { Subject } from 'rxjs';
import {AuthService} from "../../../pages/auth/service/auth.service";
import {AuthUser} from "../../../pages/auth/model/auth-user";
import {TokenLocalStorage} from "../../../pages/auth/token/token-storage";

@Component({
  selector: 'ngx-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();
  userPictureOnly: boolean = false;
  user: AuthUser;

  themes = [
    {
      value: 'default',
      name: 'Light',
    },
    {
      value: 'cosmic',
      name: 'Cosmic',
    },
  ];

  currentTheme = 'default';

  userMenu = [
    { title: 'Log out', link: '/auth/logout'}
  ];

  themeSwitcherConfig = {
    value: this.getThemeValue(),
    disabled: false,
    color: {
      checked: '#222b45',
      unchecked: '#9ea6bf',
    },
    switchColor: {
      checked: '#3366FF',
      unchecked: '#3366FF',
    },
    labels: {
      unchecked: this.themes[0].name,
      checked: this.themes[1].name,
    },
    height: 25,
    width: 65,
  };

  constructor(private sidebarService: NbSidebarService,
              private menuService: NbMenuService,
              private themeService: NbThemeService,
              private layoutService: LayoutService,
              private breakpointService: NbMediaBreakpointsService,
              private authService: AuthService, private tokenStorage: TokenLocalStorage) {
  }

  ngOnInit() {
    this.currentTheme = this.themeService.currentTheme;

    this.authService.getUser()
      .pipe(takeUntil(this.destroy$))
      .subscribe((user: any) => this.user = user);

    const { xl } = this.breakpointService.getBreakpointsMap();
    this.themeService.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);

    this.themeService.onThemeChange()
      .pipe(
        map(({ name }) => name),
        takeUntil(this.destroy$),
      )
      .subscribe(themeName => this.currentTheme = themeName);
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  changeTheme(toggleResponse: boolean) {
    this.tokenStorage.setThemeValue(toggleResponse);
    toggleResponse ?
      this.themeService.changeTheme(this.themes[1].value) : this.themeService.changeTheme(this.themes[0].value);
  }

  getThemeValue(): boolean {
    return this.tokenStorage.getThemeValue();
  }

  toggleSidebar(): boolean {
    this.sidebarService.toggle(true, 'menu-sidebar');
    this.layoutService.changeLayoutSize();

    return false;
  }

  navigateHome() {
    this.menuService.navigateHome();
    return false;
  }
}

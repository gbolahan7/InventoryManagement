/**
 * @license
 * Licensed under the MIT License. See License.txt in the project root for license information.
 */
import { Component, OnInit } from '@angular/core';
import { AnalyticsService } from './core/utils';
import {NbThemeService} from "@nebular/theme";
import {TokenLocalStorage} from "./pages/auth/token/token-storage";

@Component({
  selector: 'ngx-app',
  template: '<router-outlet></router-outlet>',
})
export class AppComponent implements OnInit {

  constructor(private analytics: AnalyticsService, private themeService: NbThemeService, private tokenService: TokenLocalStorage) {
  }

  ngOnInit(): void {
    this.analytics.trackPageViews();
    let showDefault: boolean = this.tokenService.getThemeValue();
    if(showDefault) this.themeService.changeTheme('cosmic');
    else this.themeService.changeTheme('default');
  }
}

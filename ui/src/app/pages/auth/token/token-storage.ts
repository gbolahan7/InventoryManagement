
import { Injectable } from '@angular/core';

import {AuthToken} from '../model/token';
import {AuthTokenWrapper} from "./token-wrapper";

export abstract class TokenStorage {

  abstract get(): AuthToken;
  abstract set(token: AuthToken);
  abstract clear();
}

@Injectable({
  providedIn: 'root',
})
export class TokenLocalStorage extends TokenStorage {

  protected key = 'auth_app_token';
  protected themeKey = 'theme_app_theme';

  constructor(private parceler: AuthTokenWrapper) {
    super();
  }

  get(): AuthToken {
    const raw = localStorage.getItem(this.key);
    return this.parceler.unwrap(!!raw ? JSON.parse(raw).value : null);
  }

  set(token: AuthToken) {
    const raw = this.parceler.wrap(token);
    localStorage.setItem(this.key, raw);
  }

  clear() {
    localStorage.removeItem(this.key);
  }

  getThemeValue(): boolean {
    const raw: string = localStorage.getItem(this.themeKey);
    let result: boolean;
    if(!raw) result = false;
    else if(raw === 'false') result = false;
    else result = raw === 'true';
    return result;
  }

  setThemeValue(value: boolean) {
    localStorage.setItem(this.themeKey, `${value}`);
  }
}

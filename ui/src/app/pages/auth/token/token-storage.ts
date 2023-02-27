
import { Injectable } from '@angular/core';

import {AuthToken} from '../model/token';
import {AuthTokenWrapper} from "./token-wrapper";

export abstract class TokenStorage {

  abstract get(): AuthToken;
  abstract set(token: AuthToken);
  abstract clear();
}

@Injectable()
export class TokenLocalStorage extends TokenStorage {

  protected key = 'auth_app_token';

  constructor(private parceler: AuthTokenWrapper) {
    super();
  }

  get(): AuthToken {
    const raw = localStorage.getItem(this.key);
    return this.parceler.unwrap(raw);
  }

  set(token: AuthToken) {
    const raw = this.parceler.wrap(token);
    localStorage.setItem(this.key, raw);
  }

  clear() {
    localStorage.removeItem(this.key);
  }
}


import { Injectable } from '@angular/core';
import {AuthToken, JwtAuthToken} from "../model/token";

export interface TokenPack {
  name: string,
  createdAt: Number,
  value: string,
}


@Injectable()
export class AuthTokenWrapper {

  constructor() {
  }

  wrap(token: AuthToken): string {
    return JSON.stringify({
      name: token.getName(),
      createdAt: token.getCreatedAt().getTime(),
      value: token.toString(),
    });
  }

  unwrap(value: string): AuthToken {
    const tokenPack: TokenPack = this.parseTokenPack(value);
    return new JwtAuthToken(tokenPack.value, new Date(tokenPack.createdAt.valueOf()));
  }

  protected parseTokenPack(value): TokenPack {
    try {
      return new class implements TokenPack {
        createdAt: Number = Date.now();
        name: string = 'auth';
        value: string = value;
      }
    } catch (e) { }
    return null;
  }

}

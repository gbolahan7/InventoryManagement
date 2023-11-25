export abstract class AuthToken {

  protected payload: any = null;

  abstract getValue(): string;

  abstract isValid(): boolean;

  abstract getCreatedAt(): Date;

  abstract toString(): string;

  getName(): string {
    return '_token_';
  }

  getPayload(): any {
    return this.payload;
  }
}

class AuthData {
  public token: string;
}

export class AuthCredential {
  public data: AuthData;

}

export class AuthTokenError extends Error {
  constructor(message: string) {
    super(message);
    Object.setPrototypeOf(this, new.target.prototype);
  }
}

export class JwtAuthToken extends AuthToken {

  constructor(protected readonly token: any, protected createdAt?: Date) {
    super();
    try {
      this.parsePayload();
    } catch (err) {
      if (!(err instanceof AuthTokenError)) {
        throw err;
      }
    }
    this.createdAt = this.prepareCreatedAt(createdAt);
  }

  getCreatedAt(): Date {
    return this.createdAt;
  }

  getValue(): string {
    return this.token;
  }

  isValid(): boolean {
    return !!this.getValue() &&  (!this.getTokenExpDate() || new Date() < this.getTokenExpDate());
  }

  private parsePayload(): void {
    if (!this.token) {
      throw new AuthTokenError('Token not found. ')
    }
    this.payload = this.decodeJwtPayload(this.token);
  }

  private getTokenExpDate(): Date {
    const decoded = this.getPayload();
    if (decoded && !decoded.hasOwnProperty('exp')) {
      return null;
    }
    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  private prepareCreatedAt(date: Date): Date {
    const decoded = this.getPayload();
    return decoded && decoded.iat ? new Date(Number(decoded.iat) * 1000) : new Date(date);
  }

  private decodeJwtPayload(payload: string): any {

    if (payload.length === 0) {
      throw new AuthTokenError('Cannot extract from an empty payload.');
    }

    const parts = payload.split('.');

    if (parts.length !== 3) {
      throw new AuthTokenError(
        `The payload ${payload} is not valid JWT payload and must consist of three parts.`);
    }

    let decoded;
    try {
      decoded = this.decodeToken(parts[1]);
    } catch (e) {
      throw new AuthTokenError(
        `The payload ${payload} is not valid JWT payload and cannot be parsed.`);
    }

    if (!decoded) {
      throw new AuthTokenError(
        `The payload ${payload} is not valid JWT payload and cannot be decoded.`);
    }
    return decoded;
  }

  protected decodeToken(token: string): any {
    const _decodeToken = (token) => {
      try {
        return JSON.parse(atob(token));
      } catch {
        return;
      }
    };
    return _decodeToken(token);
  }

  toString(): string {
    return !!this.token ? this.token : '';
  }

}

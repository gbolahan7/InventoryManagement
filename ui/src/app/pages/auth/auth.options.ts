import {InjectionToken} from "@angular/core";
import {HttpRequest} from "@angular/common/http";

export const AUTH_TOKEN_INTERCEPTOR_FILTER = new InjectionToken<(req: HttpRequest<any>) => boolean>('Interceptor Filter');

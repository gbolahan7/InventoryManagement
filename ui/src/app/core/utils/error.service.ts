import {Injectable} from "@angular/core";
import {NbGlobalPhysicalPosition, NbToastrService} from "@nebular/theme";
import {HttpErrorResponse} from "@angular/common/http";
import {AuthService} from "../../pages/auth/service/auth.service";


@Injectable()
export class ErrorService {

  constructor(private toastrService: NbToastrService,
              private authService: AuthService) {

  }

  handleHttpResponseError(error: HttpErrorResponse) {
    if(!error) return;
    let errorMessage: string = '';
    if (error.status == 401){
      this.showToast('Authentication error. Logging out...');
      this.authService.logout().subscribe(e => window.location.reload());
      return;
    }

    let errors = [];
    let body: any = error.error;
    if (error.status === 422) {
      errors.push(...body.data.message);
    } else if(this.isString(body)) {
      errors.push(body);
    }else errors.push('Server error');
    errorMessage = JSON.stringify(errors);
    if(errorMessage) this.showToast(errorMessage);
    return  error;
  }

  private isString (obj): boolean {
    return (Object.prototype.toString.call(obj) === '[object String]');
  }

  private showToast( body: string) {
    const config = {
      status: 'warning',
      destroyByClick: true,
      duration: 6000,
      hasIcon: true,
      position: NbGlobalPhysicalPosition.TOP_RIGHT,
      preventDuplicates: false,
    };

    this.toastrService.show(body, 'An error has occurred', config);
  }

}

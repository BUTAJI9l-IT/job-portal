import {Injectable} from '@angular/core';
import {ErrorResponse} from "../../model/errorResponse";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TranslateService} from "@ngx-translate/core";

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  constructor(private _snackBar: MatSnackBar, private translate: TranslateService,) {
  }

  public handleErrorCode(err: ErrorResponse): boolean {
    if (err.type) {
      const code = err.type.split('/').at(-1);
      let key = "errorCodes." + code;
      if (this.translate.translations[this.translate.currentLang][key] !== undefined) {
        this.showMessage(key);
        return true;
      }
    }
    return false;
  }

  public showMessage(message: string): void;
  public showMessage(message: string, confirmationMessage: string): void;

  public showMessage(message: string, confirmationMessage?: string): void {
    this._snackBar.open(this.translate.instant(message), this.translate.instant(confirmationMessage ? confirmationMessage : "alerts.common.ok"));
  }

  public showCommonError(): void {
    this.showMessage("alerts.common.error");
  }

  public commonErrorHandle(err: ErrorResponse): void {
    if (!this.handleErrorCode(err)) {
      this.showMessage("alerts.common.error");
    }
  }
}

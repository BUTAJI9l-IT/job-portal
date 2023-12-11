import {Injectable} from '@angular/core';
import {ErrorResponse} from "../../model/errorResponse";
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  constructor(private _snackBar: MatSnackBar) {
  }

  public handleErrorCode(err: ErrorResponse): boolean {
    if (err.type) {
      const code = err.type.split('/').at(-1);
      this.showMessage("Error occurred: " + code);
      return true;
    }
    return false;
  }

  public showMessage(message: string): void;
  public showMessage(message: string, confirmationMessage: string): void;

  public showMessage(message: string, confirmationMessage?: string): void {
    this._snackBar.open(message, confirmationMessage ? confirmationMessage : "OK");
  }

  public showCommonError(): void {
    this.showMessage("Error occurred");
  }

  public commonErrorHandle(err: ErrorResponse): void {
    if (!this.handleErrorCode(err)) {
      this.showMessage("Error occurred");
    }
  }
}

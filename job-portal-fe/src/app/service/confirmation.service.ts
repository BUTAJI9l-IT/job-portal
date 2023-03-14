import {Injectable} from '@angular/core';
import {ConfirmationComponent} from "../component/dialog/confirmation/confirmation.component";
import {MatDialog} from "@angular/material/dialog";

@Injectable({
  providedIn: 'root'
})
export class ConfirmationService {
  confirmationOpened = false;

  constructor(public dialog: MatDialog) {
  }

  openConfirm(confirmationText: string | null | undefined, onSubmit: () => void) {
    const confirmation = this.dialog.open(ConfirmationComponent);
    confirmation.componentInstance.confirmationText = confirmationText ? confirmationText : "dialog.login.confirmation.text";
    confirmation.afterOpened().subscribe(() => {
      this.confirmationOpened = true;
    })
    confirmation.afterClosed().subscribe(() => {
      this.confirmationOpened = false;
    })
    confirmation.componentInstance.confirmation.subscribe((result) => {
      confirmation.close();
      if (result) {
        return onSubmit();
      }
    })
  }

  isOpened() {
    return this.confirmationOpened;
  }
}

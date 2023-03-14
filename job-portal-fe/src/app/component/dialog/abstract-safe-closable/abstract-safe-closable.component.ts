import {Component, HostListener, Inject} from '@angular/core';
import {ConfirmationService} from "../../../service/confirmation.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {MyErrorStateMatcher} from "../../../state/error/my-error-state-matcher";

@Component({template: ''})
export abstract class AbstractSafeClosableComponent<T> {
  confirmationText?: string;
  matcher = new MyErrorStateMatcher();
  userId: string = '';

  protected constructor(
    public confirmationService: ConfirmationService,
    public translate: TranslateService,
    protected dialogRef: MatDialogRef<T>,
    @Inject(MAT_DIALOG_DATA) protected _data?: any
  ) {
    this.dialogRef.disableClose = true;
    this.dialogRef.backdropClick().subscribe(() => {
      this.safeClose();
    })
    if (_data?.userId) {
      this.userId = _data.userId;
    }
  }

  @HostListener('window:keyup.esc') onKeyUp() {
    if (!this.confirmationService.isOpened()) {
      this.safeClose();
    }
  }

  safeClose(): void {
    const hasChange = this.getHasChange();
    if (hasChange) {
      this.confirmationService.openConfirm(this.confirmationText, () => {
        this.close();
      });
    } else {
      this.close();
    }
  }

  close(): void {
    this.dialogRef.close();
  }

  protected getHasChange(): boolean {
    return false;
  }
}

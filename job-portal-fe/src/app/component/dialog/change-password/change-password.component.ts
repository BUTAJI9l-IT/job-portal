import {Component, Inject} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {passwordValidators} from "../../input/password/password.component";
import {passwordRepeatValidators} from "../../input/password/repeat/repeat.component";
import {AbstractSafeClosableComponent} from "../abstract-safe-closable/abstract-safe-closable.component";
import {ConfirmationService} from "../../../service/confirmation.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {AlertService} from "../../../service/alert.service";
import {UserService} from "../../../../api/user.service";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent extends AbstractSafeClosableComponent<ChangePasswordComponent> {
  oldPassword = new FormControl('', passwordValidators);
  password = new FormGroup({
    password: new FormControl('', passwordValidators),
    repeatPassword: new FormControl('', [Validators.required])
  }, passwordRepeatValidators)

  constructor(
    confirmationService: ConfirmationService,
    dialogRef: MatDialogRef<ChangePasswordComponent>,
    translate: TranslateService,
    private alertService: AlertService,
    private userService: UserService,
    @Inject(MAT_DIALOG_DATA) protected data?: any
  ) {
    super(confirmationService, translate, dialogRef, data);
  }

  changePassword() {
    if (this.oldPassword.invalid || this.password.invalid || this.password.controls.password.invalid || this.password.controls.repeatPassword.invalid) {
      this.oldPassword.markAsDirty();
      this.password.markAsDirty();
      this.password.controls.password.markAsDirty();
      this.password.controls.repeatPassword.markAsDirty();
      return;
    }
    this.userService.updatePassword({
      oldPassword: this.oldPassword.value!,
      password: {
        password: this.password.controls['password'].value!,
        repeatPassword: this.password.controls['repeatPassword'].value!,
      }
    }, this.userId).subscribe({
      next: () => {
        this.close();
        this.alertService.showMessage("account.common.tabs.settings.change-password.success");
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    })
  }
}

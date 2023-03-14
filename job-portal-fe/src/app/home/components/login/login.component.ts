import {Component} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {LoginRequest} from "../../../../model/loginRequest";
import {RegistrationRequest} from "../../../../model/registrationRequest";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {passwordValidators} from "../../../component/input/password/password.component";
import {emailValidators} from "../../../component/input/email/email.component";
import {passwordRepeatValidators} from "../../../component/input/password/repeat/repeat.component";
import {AuthorizationService} from "../../../../api/authorization.service";
import {StorageService} from "../../../service/storage.service";
import {LoginResponse} from "../../../../model/loginResponse";
import {TranslateService} from "@ngx-translate/core";
import {ErrorResponse} from "../../../../model/errorResponse";
import {slideInOut} from "../../../animations";
import {ConfirmationService} from "../../../service/confirmation.service";
import {AlertService} from "../../../service/alert.service";
import {
  AbstractSafeClosableComponent
} from "../../../component/dialog/abstract-safe-closable/abstract-safe-closable.component";
import ScopeEnum = RegistrationRequest.ScopeEnum;

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  animations: [slideInOut]
})
export class LoginComponent extends AbstractSafeClosableComponent<LoginComponent> {
  cSizes = [
    {value: RegistrationRequest.CompanySizeEnum.MICRO},
    {value: RegistrationRequest.CompanySizeEnum.SMALL},
    {value: RegistrationRequest.CompanySizeEnum.MEDIUM},
    {value: RegistrationRequest.CompanySizeEnum.LARGE},
    {value: RegistrationRequest.CompanySizeEnum.CORPORATION}
  ];
  emailFormControlLogin = new FormGroup({
    email: new FormControl('', emailValidators),
    password: new FormControl('', passwordValidators)
  });

  formControlRegistration = new FormGroup({
    email: new FormControl('', emailValidators),
    password: new FormGroup({
      password: new FormControl('', passwordValidators),
      repeatPassword: new FormControl('', [Validators.required])
    }, passwordRepeatValidators),
    isCompany: new FormControl(false),
    name: new FormControl(''),
    lastName: new FormControl(''),
    companyLink: new FormControl(''),
    companyName: new FormControl(''),
    companySize: new FormControl<RegistrationRequest.CompanySizeEnum | null>(null),
  });

  constructor(
    confirmationService: ConfirmationService,
    dialogRef: MatDialogRef<LoginComponent>,
    translate: TranslateService,
    private alertService: AlertService,
    private authService: AuthorizationService,
    private storageService: StorageService
  ) {
    super(confirmationService, translate, dialogRef);
  }

  protected override getHasChange() {
    return !!(this.formControlRegistration.controls.email.value ||
      this.formControlRegistration.controls.name.value ||
      this.formControlRegistration.controls.lastName.value ||
      this.formControlRegistration.controls.password.controls.password.value ||
      this.formControlRegistration.controls.password.controls.repeatPassword.value ||
      this.formControlRegistration.controls.companyName.value ||
      this.formControlRegistration.controls.companyLink.value ||
      this.formControlRegistration.controls.companySize.value);
  }

  submitLogin() {
    if (this.emailFormControlLogin.invalid) {
      for (let inner in this.emailFormControlLogin.controls) {
        this.emailFormControlLogin.get(inner)?.markAsDirty();
      }
      return;
    }

    const loginRequest: LoginRequest = {
      email: this.emailFormControlLogin.value.email!,
      password: this.emailFormControlLogin.value.password!
    }

    this.authService.login(loginRequest).subscribe({
        next: response => {
          this.performLogin(response);
        },
        error: err => {
          this.handleLoginError(err.error);
        }
      }
    );
  }

  performLogin(response: LoginResponse) {
    this.storageService.saveTokens(response);
    this.dialogRef.close()
    this.refresh();
    this.alertService.showMessage("alerts.login.success");
  }

  submitRegistration() {
    if (this.formControlRegistration.invalid) {
      for (let inner in this.formControlRegistration.controls) {
        this.formControlRegistration.get(inner)?.markAsDirty();
      }
      this.formControlRegistration.controls.password.controls.password.markAsDirty();
      this.formControlRegistration.controls.password.controls.repeatPassword.markAsDirty();
      return;
    }

    const registrationRequest: RegistrationRequest = {
      companyName: this.formControlRegistration.value.companyName!,
      companyLink: this.formControlRegistration.value.companyLink!,
      companySize: this.formControlRegistration.value.companySize!,
      email: this.formControlRegistration.value.email!,
      lastName: this.formControlRegistration.value.lastName!,
      name: this.formControlRegistration.value.name!,
      password: {
        password: this.formControlRegistration.value.password?.password!,
        repeatPassword: this.formControlRegistration.value.password?.repeatPassword!,
      },
      scope: this.formControlRegistration.value.isCompany ? ScopeEnum.COMPANY : ScopeEnum.REGULARUSER,
      language: this.translate.currentLang
    }

    this.authService.register(registrationRequest).subscribe({
        next: response => {
          this.performLogin(response);
        },
        error: err => {
          this.handleLoginError(err.error);
        }
      }
    );
  }

  private handleLoginError(err: ErrorResponse) {
    if (!this.alertService.handleErrorCode(err)) {
      if (err.status === 401) {
        this.alertService.showMessage("alerts.login.error.credentials");
      } else
        this.alertService.showCommonError();
    }
  }

  refresh(): void {
    window.location.reload();
  }
}

import {Component} from '@angular/core';
import {RegistrationRequest} from "../../../../model/registrationRequest";
import {AbstractSafeClosableComponent} from "../abstract-safe-closable/abstract-safe-closable.component";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {emailValidators} from "../../input/email/email.component";
import {ConfirmationService} from "../../../service/confirmation.service";
import {MatDialogRef} from "@angular/material/dialog";
import {AlertService} from "../../../service/alert.service";
import {AuthorizationService} from "../../../../api/authorization.service";
import {UserDto} from "../../../../model/userDto";
import {passwordValidators} from "../../input/password/password.component";
import {passwordRepeatValidators} from "../../input/password/repeat/repeat.component";
import {ErrorResponse} from "../../../../model/errorResponse";
import {slideInOut} from "../../../animations";
import ScopeEnum = UserDto.ScopeEnum;

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css'],
  animations: [slideInOut]
})
export class AddUserComponent extends AbstractSafeClosableComponent<AddUserComponent>{
  cSizes = [
    {value: RegistrationRequest.CompanySizeEnum.MICRO},
    {value: RegistrationRequest.CompanySizeEnum.SMALL},
    {value: RegistrationRequest.CompanySizeEnum.MEDIUM},
    {value: RegistrationRequest.CompanySizeEnum.LARGE},
    {value: RegistrationRequest.CompanySizeEnum.CORPORATION}
  ];

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
    language: new FormControl<string>("en"),
  });
  languages = ["en", "cs", "sk", "ru"]

  constructor(
      confirmationService: ConfirmationService,
      dialogRef: MatDialogRef<AddUserComponent>,
      private alertService: AlertService,
      private authService: AuthorizationService) {
    super(confirmationService, dialogRef);
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

  performLogin() {
    this.refresh();
    this.alertService.showMessage("User has been created");
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
      language: this.formControlRegistration.value.language!
    }

    this.authService.register(registrationRequest).subscribe({
          next: () => {
            this.performLogin();
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
        this.alertService.showMessage("Bad credentials");
      } else
        this.alertService.showCommonError();
    }
  }

  refresh(): void {
    window.location.reload();
  }
}

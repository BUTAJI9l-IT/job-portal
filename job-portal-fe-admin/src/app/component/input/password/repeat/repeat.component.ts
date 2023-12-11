import {Component, Input} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn} from "@angular/forms";

export const checkPasswordsValidator: ValidatorFn = (group: AbstractControl): ValidationErrors | null => {
  let pass = group.get('password');
  let passVal = pass === null ? null : pass.value;
  let confirmPass = group.get('repeatPassword');
  let confirmPassVal = confirmPass === null ? null : confirmPass.value;
  if (confirmPass !== null) {
    confirmPass.setErrors(passVal === confirmPassVal ? null : {notSame: true});
  }
  return passVal === confirmPassVal ? null : {notSame: true}
}

export const passwordRepeatValidators = [checkPasswordsValidator]

@Component({
  selector: 'repeat-password',
  templateUrl: './repeat.component.html',
  styleUrls: ['./repeat.component.css']
})
export class RepeatComponent {
  @Input()
  repeatPasswordFormGroup: FormGroup<{ password: FormControl<any>, repeatPassword: FormControl<any> }> = new FormGroup({
    password: new FormControl(),
    repeatPassword: new FormControl()
  });
}

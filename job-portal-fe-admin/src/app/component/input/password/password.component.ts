import {Component, Input} from '@angular/core';
import {Validators} from "@angular/forms";
import {EditableInputComponent} from "../editable-input/editable-input.component";

export const passwordValidators = [Validators.required, Validators.minLength(8)];

@Component({
  selector: 'password-input',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent extends EditableInputComponent {
  hide = true;

  @Input()
  override label: string = "inputs.password.label";
}

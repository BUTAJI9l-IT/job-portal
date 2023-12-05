import {Component} from '@angular/core';
import {Validators} from "@angular/forms";
import {EditableInputComponent} from "../editable-input/editable-input.component";

export const emailValidators = [Validators.required, Validators.email];

@Component({
  selector: 'email-input',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent extends EditableInputComponent {}

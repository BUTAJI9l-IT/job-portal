import {Component} from '@angular/core';
import {Validators} from "@angular/forms";
import {EditableInputComponent} from "../editable-input/editable-input.component";

export const phoneValidators = [Validators.pattern('[- +()0-9]+')];

@Component({
  selector: 'phone-input',
  templateUrl: './phone.component.html',
  styleUrls: ['./phone.component.css']
})
export class PhoneComponent extends EditableInputComponent {
}

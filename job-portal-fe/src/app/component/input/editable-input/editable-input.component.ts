import {Component, Input} from '@angular/core';
import {MyErrorStateMatcher} from "../../../state/error/my-error-state-matcher";
import {FormControl} from "@angular/forms";
import {MatFormFieldAppearance} from "@angular/material/form-field";

@Component({template: ""})
export class EditableInputComponent {
  matcher = new MyErrorStateMatcher();

  @Input()
  formControlComponent: FormControl<any> = new FormControl();

  @Input()
  editMode: boolean = true;
  @Input()
  fieldAppearance: MatFormFieldAppearance = "fill";
  @Input()
  fontSize: string | undefined | null;
  @Input()
  label: string = '';

  constructor() {
  }
}

import {Component, Input} from '@angular/core';
import {EditableInputComponent} from "../editable-input/editable-input.component";

@Component({
  selector: 'text-input',
  templateUrl: './text.component.html',
  styleUrls: ['./text.component.css']
})
export class TextComponent extends EditableInputComponent {
  @Input()
  inputType = '';
}

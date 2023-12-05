import {Component} from '@angular/core';
import {EditableInputComponent} from "../editable-input/editable-input.component";

@Component({
  selector: 'text-area-input',
  templateUrl: './text-area.component.html',
  styleUrls: ['./text-area.component.css']
})
export class TextAreaComponent extends EditableInputComponent {
}

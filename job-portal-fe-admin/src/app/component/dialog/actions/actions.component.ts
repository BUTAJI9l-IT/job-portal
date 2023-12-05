import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AbstractControl, FormControl} from "@angular/forms";

export declare type AlignType = 'start' | 'center' | 'end';

@Component({
  selector: 'dialog-actions',
  templateUrl: './actions.component.html',
  styleUrls: ['./actions.component.css']
})
export class ActionsComponent {
  @Output() onSubmit: EventEmitter<any> = new EventEmitter();
  @Output() onCancel: EventEmitter<any> = new EventEmitter();

  @Input()
  submitText: string = "Submit";
  @Input()
  closeText: string = "Close";
  @Input()
  ngForm: AbstractControl = new FormControl();
  @Input()
  alignButtons: AlignType = "end";

  submit() {
    this.onSubmit.emit();
  }

  cancel() {
    this.onCancel.emit();
  }
}

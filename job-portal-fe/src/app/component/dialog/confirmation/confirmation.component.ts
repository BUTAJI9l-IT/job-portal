import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {AbstractControl, FormControl} from "@angular/forms";
import {AlignType} from "../actions/actions.component";

@Component({
  selector: 'confirm-action-modal',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
export class ConfirmationComponent {
  @Output() confirmation: EventEmitter<boolean> = new EventEmitter();
  submitText: string = "dialog.confirmation.submit";
  closeText: string = "dialog.confirmation.close";
  confirmationText: string = "dialog.confirmation.text";
  dialogLabel: string = "dialog.confirmation.title";

  constructor(
    public dialogRef: MatDialogRef<ConfirmationComponent>
  ) {
    this.dialogRef.disableClose = true;
  }

  submit() {
    this.confirmation.emit(true);
  }

  cancel() {
    this.confirmation.emit(false);
  }

}

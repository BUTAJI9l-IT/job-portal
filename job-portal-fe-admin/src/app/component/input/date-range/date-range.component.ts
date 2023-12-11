import {Component, Inject, Input, ViewChild} from '@angular/core';
import {EditableInputComponent} from "../editable-input/editable-input.component";
import {FormControl, FormGroup} from "@angular/forms";
import {DateAdapter, MAT_DATE_LOCALE} from "@angular/material/core";

@Component({
  selector: 'date-range',
  templateUrl: './date-range.component.html',
  styleUrls: ['./date-range.component.css']
})
export class DateRangeComponent extends EditableInputComponent {
  @Input()
  dateRange = new FormGroup({
    from: new FormControl<Date | null>(null),
    to: new FormControl<Date | null>(null),
  });

  constructor(
    private _adapter: DateAdapter<any>,
    @Inject(MAT_DATE_LOCALE) private _locale: string,
  ) {
    super();
    this._adapter.setLocale(this._locale);
  }
}

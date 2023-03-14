import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormArray, FormControl, FormGroup} from "@angular/forms";
import {MatDialog} from "@angular/material/dialog";
import {AddExperienceComponent} from "../../dialog/add-experience/add-experience.component";

@Component({
  selector: 'experience-card-list',
  templateUrl: './experience-card-list.component.html',
  styleUrls: ['./experience-card-list.component.css']
})
export class ExperienceCardListComponent {
  @Output()
  updateExperiences: EventEmitter<void> = new EventEmitter<void>();
  @Input()
  width: string = "100%";
  @Input()
  height: string = "100%";
  @Input()
  experiences: FormArray<FormGroup> = new FormArray<FormGroup>([]);
  @Input()
  readOnly = true;
  @Input()
  userId: string = '';

  constructor(public dialog: MatDialog) {
  }

  addExperience() {
    this.dialog.open(AddExperienceComponent, {
      data: {
        userId: this.userId
      }
    }).afterClosed().subscribe((res: boolean) => {
      if (res) {
        this.updateExperiences.emit();
      }
    })
  }
}

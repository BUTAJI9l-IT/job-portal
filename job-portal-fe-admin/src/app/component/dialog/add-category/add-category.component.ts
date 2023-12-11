import { Component } from '@angular/core';
import {AbstractSafeClosableComponent} from "../abstract-safe-closable/abstract-safe-closable.component";
import {ConfirmationService} from "../../../service/confirmation.service";
import {MatDialogRef} from "@angular/material/dialog";
import {AlertService} from "../../../service/alert.service";
import {JobPositionService} from "../../../../api/jobPosition.service";
import {JobPositionCategoryService} from "../../../../api/jobPositionCategory.service";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-add-category',
  templateUrl: './add-category.component.html',
  styleUrls: ['./add-category.component.css']
})
export class AddCategoryComponent extends AbstractSafeClosableComponent<AddCategoryComponent>{
  categoryForm = new FormControl(null)
  constructor(
      confirmationService: ConfirmationService,
      dialogRef: MatDialogRef<AddCategoryComponent>,
      private alertService: AlertService,
      private categoryService: JobPositionCategoryService) {
    super(confirmationService, dialogRef);
  }
  saveCategory() {
    if (!this.categoryForm.value) {
      return
    }
    this.categoryService.createCategory(this.categoryForm.value).subscribe({
          next: () => {
            window.location.reload()
            this.alertService.showMessage("New category has been created");
          },
          error: err => {
            this.alertService.handleErrorCode(err.error)
          }
        }
    );
  }
}

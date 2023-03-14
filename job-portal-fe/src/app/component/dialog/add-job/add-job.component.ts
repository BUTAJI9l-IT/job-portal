import {AfterViewInit, Component, Inject, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ReferenceDto} from "../../../../model/referenceDto";
import {CompanyDto} from "../../../../model/companyDto";
import {ConfirmationService} from "../../../service/confirmation.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {JobPositionCategoryService} from "../../../../api/jobPositionCategory.service";
import {StorageService} from "../../../service/storage.service";
import {AlertService} from "../../../service/alert.service";
import {CompanyService} from "../../../../api/company.service";
import {AbstractSafeClosableComponent} from "../abstract-safe-closable/abstract-safe-closable.component";
import {emailValidators} from "../../input/email/email.component";
import {JobPositionCreateDto} from "../../../../model/jobPositionCreateDto";
import {JobPositionService} from "../../../../api/jobPosition.service";
import {JobPositionUpdateDto} from "../../../../model/jobPositionUpdateDto";

@Component({
  selector: 'app-add-job',
  templateUrl: './add-job.component.html',
  styleUrls: ['./add-job.component.css']
})
export class AddJobComponent extends AbstractSafeClosableComponent<AddJobComponent> implements OnInit, AfterViewInit {

  jobPosition = new FormGroup({
    occupation: new FormControl<ReferenceDto | string>(''),
    contactEmail: new FormControl<string>(''),
    detail: new FormControl<string>(''),
    company: new FormControl<CompanyDto | string>(''),

    country: new FormControl(''),
    state: new FormControl(''),
    city: new FormControl(''),
  });
  override confirmationText?: string = "dialog.confirmation.jobPosition.add-new";
  jobCategoriesSelected: ReferenceDto[] = [];
  occupations: ReferenceDto[] = [];
  companies: CompanyDto[] = [];
  categories: ReferenceDto[] = [];
  update = false;
  data: FormGroup | undefined;

  constructor(
    confirmationService: ConfirmationService,
    dialogRef: MatDialogRef<AddJobComponent>,
    translate: TranslateService,
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private categoryService: JobPositionCategoryService,
    private jobPositionService: JobPositionService,
    private storage: StorageService,
    private alertService: AlertService,
    private companyService: CompanyService
  ) {
    super(confirmationService, translate, dialogRef, dialogData);
    this.dialogRef.updateSize("50%")
  }

  override getHasChange(): boolean {
    const occupation = typeof this.jobPosition.controls.occupation.value === 'string' ? this.jobPosition.controls.occupation.value : this.jobPosition.controls.occupation.value?.name;
    return !!(
      occupation ||
      this.jobPosition.controls.detail.value ||
      this.jobPosition.controls.country.value ||
      this.jobPosition.controls.state.value ||
      this.jobPosition.controls.city.value
    );
  }

  ngOnInit(): void {
    this.data = this.dialogData?.job;
    if (this.data) {
      this.jobPosition.controls.company.setValue({
        id: this.data.controls['company'].get('id')!.value,
        companyName: this.data.controls['company'].get('companyName')!.value,
      });
      this.jobPosition.controls.detail.setValue(this.data.controls['detail'].value);
      let name: string = this.data.controls['positionName'].value;
      this.jobPosition.controls.occupation.setValue({id: undefined, name: name});
      this.jobPosition.controls.city.setValue(this.data.controls['city'].value);
      this.jobPosition.controls.country.setValue(this.data.controls['country'].value);
      this.jobPosition.controls.state.setValue(this.data.controls['state'].value);
      this.jobPosition.controls.contactEmail.setValue(this.data.controls['contactEmail'].value);
      let data: any = this.data
      this.jobCategoriesSelected = data.get('jobCategories').controls.map((jcat: FormGroup) => {
        return {id: jcat.controls['id'].value, name: jcat.controls['name'].value}
      });
      this.update = true;
      this.confirmationText = 'dialog.confirmation.jobPosition.update'
    }
    this.jobPosition.controls.occupation.addValidators([Validators.required]);
    this.jobPosition.controls.company.addValidators([Validators.required]);
    this.jobPosition.controls.contactEmail.addValidators(emailValidators);

    this.jobPosition.controls.occupation.valueChanges.subscribe((value) => {
      const name = typeof value === 'string' ? value : value?.name;
      this.categoryService.getOccupations(name ? [name] : []).subscribe((resp) => {
        this.occupations = resp.content ? resp.content : [];
      });
    })
    if (this.storage.getTokenInfo()?.scope !== "ADMIN") {
      if (this.storage.getTokenInfo()?.scope === "COMPANY") {
        this.companyService.getCompany(this.userId).subscribe((resp) => {
          this.jobPosition.controls.company.setValue({companyName: resp.companyName, id: resp.id});
          this.jobPosition.controls.contactEmail.setValue(resp.email!);
        });
        this.jobPosition.controls.company.disable();
      }
    } else {
      if (this.userId) {
        this.companyService.getCompany(this.userId).subscribe((resp) => {
          this.jobPosition.controls.company.setValue({companyName: resp.companyName, id: resp.id});
          this.jobPosition.controls.contactEmail.setValue(resp.email!);
        });
      }
      this.jobPosition.controls.company.valueChanges.subscribe((value) => {
        const name = typeof value === 'string' ? value : value?.companyName;
        this.companyService.getCompanies(name ? [name] : []).subscribe((resp) => {
          this.companies = resp.content ? resp.content : [];
        });
      })
    }
    this.categoryService.getCategories().subscribe(value => this.categories = value.jobCategories ? value.jobCategories : []);
  }

  occupationTranslate = (value: ReferenceDto) => {
    if (value.id) {
      return this.translate.instant('categories.occupations.' + value.id);
    }
    if (this.update && value.name) {
      return value.name
    }
    return ''
  }
  companyDisplay = (value: any) => {
    return value.companyName;
  };

  public equalsCat(objOne: ReferenceDto, objTwo: ReferenceDto) {
    return objOne.id === objTwo.id;
  }

  private updateJob() {
    if (!!this.data) {
      const occupation = typeof this.jobPosition.controls.occupation.value === 'string' ? this.jobPosition.controls.occupation.value : this.jobPosition.controls.occupation.value?.name;
      const request: JobPositionUpdateDto = {
        positionName: occupation!,
        jobCategories: this.jobCategoriesSelected.map(cat => cat.id!)!,
        state: this.jobPosition.controls.state.value!,
        country: this.jobPosition.controls.country.value!,
        city: this.jobPosition.controls.city.value!,
        detail: this.jobPosition.controls.detail.value!,
        contactEmail: this.jobPosition.controls.contactEmail.value!,
        status: this.data.controls['status'].value!
      }
      this.jobPositionService.updateJobPosition(request, this.data.controls['id'].value!).subscribe({
        next: () => {
          if (this.data) {
            let data: any = this.data;
            let arr = new FormArray(this.jobCategoriesSelected.map(cat => {
              return new FormGroup({
                id: new FormControl(cat.id ? cat.id : ''),
                name: new FormControl(cat.name ? cat.name : '')
              })!;
            }));
            data.controls['jobCategories'] = arr
            this.data.controls['detail'].setValue(request.detail);
            this.data.controls['positionName'].setValue(request.positionName);
            this.data.controls['city'].setValue(request.city);
            this.data.controls['country'].setValue(request.country);
            this.data.controls['state'].setValue(request.state);
            this.data.controls['contactEmail'].setValue(request.contactEmail);

            this.dialogRef.close(true);
          }
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error);
          this.alertService.showMessage("alerts.jobPosition.updated");
        }
      });
    }
  }

  createJobPosition() {
    if (this.update) {
      this.updateJob()
      return;
    }
    if (this.jobPosition.controls.occupation.invalid || this.jobPosition.controls.company.invalid || this.jobPosition.controls.contactEmail.invalid) {
      this.jobPosition.controls.occupation.markAsDirty();
      this.jobPosition.controls.company.markAsDirty();
      this.jobPosition.controls.contactEmail.markAsDirty();
      return;
    }
    if ((typeof this.jobPosition.controls.company.value === 'string') || (!this.jobPosition.controls.company.value?.id)) {
      this.jobPosition.controls.company.reset('');
      this.jobPosition.controls.company.markAsDirty();
      return;
    }

    const occupation = typeof this.jobPosition.controls.occupation.value === 'string' ? this.jobPosition.controls.occupation.value : this.jobPosition.controls.occupation.value?.name;
    const company = this.jobPosition.controls.company.value?.id!;
    const request: JobPositionCreateDto = {
      positionName: occupation!,
      company: company,
      jobCategories: this.jobCategoriesSelected.map(cat => cat.id!)!,
      state: this.jobPosition.controls.state.value!,
      country: this.jobPosition.controls.country.value!,
      city: this.jobPosition.controls.city.value!,
      detail: this.jobPosition.controls.detail.value!,
      contactEmail: this.jobPosition.controls.contactEmail.value!
    }
    this.jobPositionService.createJobPosition(request).subscribe({
      next: () => {
        this.dialogRef.close(true);
        this.alertService.showMessage("alerts.jobPosition.added");
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    });
  }

  ngAfterViewInit(): void {

  }
}

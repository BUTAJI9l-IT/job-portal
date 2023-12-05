import {Component, Inject, OnInit} from '@angular/core';
import {AbstractSafeClosableComponent} from "../abstract-safe-closable/abstract-safe-closable.component";
import {ConfirmationService} from "../../../service/confirmation.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {TranslateService} from "@ngx-translate/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ReferenceDto} from "../../../../model/referenceDto";
import {JobPositionCategoryService} from "../../../../api/jobPositionCategory.service";
import {CompanyDto} from "../../../../model/companyDto";
import {CompanyService} from "../../../../api/company.service";
import {ApplicantService} from "../../../../api/applicant.service";
import {StorageService} from "../../../service/storage.service";
import {ExperienceDto} from "../../../../model/experienceDto";
import {AlertService} from "../../../service/alert.service";

@Component({
  selector: 'add-experience',
  templateUrl: './add-experience.component.html',
  styleUrls: ['./add-experience.component.css']
})
export class AddExperienceComponent extends AbstractSafeClosableComponent<AddExperienceComponent> implements OnInit {
  experience = new FormGroup({
    occupation: new FormControl<ReferenceDto | string>(''),
    company: new FormControl<CompanyDto | string>({
      id: '',
      companyName: ''
    }),
    dateRange: new FormGroup({
      from: new FormControl<Date | null>(null),
      to: new FormControl<Date | null>(null),
    })
  });
  override confirmationText?: string = "dialog.confirmation.experience.add-new";
  jobCategoriesSelected: ReferenceDto[] = [];

  occupations: ReferenceDto[] = []

  companies: CompanyDto[] = []

  categories: ReferenceDto[] = [];

  constructor(
    confirmationService: ConfirmationService,
    dialogRef: MatDialogRef<AddExperienceComponent>,
    translate: TranslateService,
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private categoryService: JobPositionCategoryService,
    private applicantService: ApplicantService,
    private storage: StorageService,
    private alertService: AlertService,
    private companyService: CompanyService
  ) {
    super(confirmationService, translate, dialogRef, dialogData);
    this.dialogRef.updateSize("50%")
  }

  override getHasChange(): boolean {
    const name = typeof this.experience.controls.occupation.value === 'string' ? this.experience.controls.occupation.value : this.experience.controls.occupation.value?.name;
    const companyName = typeof this.experience.controls.company.value === 'string' ? this.experience.controls.company.value : this.experience.controls.company.value?.companyName;
    return !!(
      name || companyName ||
      this.experience.controls.dateRange.controls.from.value ||
      this.experience.controls.dateRange.controls.to.value ||
      this.jobCategoriesSelected.length > 0
    );
  }

  ngOnInit(): void {
    this.experience.controls.occupation.addValidators([Validators.required]);
    this.experience.controls.occupation.valueChanges.subscribe((value) => {
      const name = typeof value === 'string' ? value : value?.name;
      this.categoryService.getOccupations(name ? [name] : []).subscribe((resp) => {
        this.occupations = resp.content ? resp.content : [];
      });
    })
    this.experience.controls.company.valueChanges.subscribe((value) => {
      const name = typeof value === 'string' ? value : value?.companyName;
      this.companyService.getCompanies(name ? [name] : []).subscribe((resp) => {
        this.companies = resp.content ? resp.content : [];
      });
    })
    this.categoryService.getCategories().subscribe(value => this.categories = value.jobCategories ? value.jobCategories : []);
  }

  occupationTranslate = (value: ReferenceDto) => {
    if (value.id) {
      return this.translate.instant('categories.occupations.' + value.id);
    }
    return ''
  }
  companyDisplay = (value: any) => {
    return value.companyName;
  };

  public equalsCat(objOne: ReferenceDto, objTwo: ReferenceDto) {
    return objOne.id === objTwo.id;
  }

  createExperience() {
    if (this.experience.controls.occupation.invalid || this.experience.controls.dateRange.controls.from.hasError('matStartDateInvalid') || this.experience.controls.dateRange.controls.to.hasError('matEndDateInvalid')) {
      this.experience.controls.occupation.markAsDirty();
      return;
    }
    const occupation = typeof this.experience.controls.occupation.value === 'string' ? this.experience.controls.occupation.value : this.experience.controls.occupation.value?.name;
    const company = typeof this.experience.controls.company.value === 'string' ? {
      companyName: this.experience.controls.company.value
    } : {
      companyName: this.experience.controls.company.value?.companyName,
      id: this.experience.controls.company.value?.id
    };
    let fromDate = this.experience.controls.dateRange.controls.from.value;
    let toDate = this.experience.controls.dateRange.controls.to.value;
    const request: ExperienceDto = {
      occupation: occupation!,
      company: company,
      jobCategories: this.jobCategoriesSelected,
      dateRange: {
        fromDate: fromDate ? new Date(fromDate.getTime() - (fromDate.getTimezoneOffset() * 60000)).toISOString().split('T')[0] : undefined,
        toDate: toDate ? new Date(toDate.getTime() - (toDate.getTimezoneOffset() * 60000)).toISOString().split('T')[0] : undefined
      }
    }
    this.applicantService.addExperience(request, this.userId).subscribe({
      next: () => {
        this.dialogRef.close(true);
        this.alertService.showMessage("alerts.experience.added");
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    });
  }
}

import {Component} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ApplicantDetailDto} from "../../../../model/applicantDetailDto";
import {ExperienceDto} from "../../../../model/experienceDto";
import {ConfirmationService} from "../../../service/confirmation.service";
import {AlertService} from "../../../service/alert.service";
import {ActivatedRoute} from "@angular/router";
import {StorageService} from "../../../service/storage.service";
import {UserService} from "../../../../api/user.service";
import {CompanyService} from "../../../../api/company.service";
import {DomSanitizer} from "@angular/platform-browser";
import {ApplicationService} from "../../../../api/application.service";
import {ApplicationDto} from "../../../../model/applicationDto";
import StateEnum = ApplicationDto.StateEnum;

@Component({
  selector: 'app-job-applicant-card',
  templateUrl: './job-applicant-card.component.html',
  styleUrls: ['./job-applicant-card.component.css']
})
export class JobApplicantCardComponent {

  applicantInfo: FormGroup = new FormGroup({
    id: new FormControl(''),
    email: new FormControl(''),
    name: new FormControl(''),
    lastName: new FormControl(''),
    profile: new FormControl(''),
    phone: new FormControl(''),
    country: new FormControl(''),
    state: new FormControl(''),
    city: new FormControl(''),
    experiences: new FormArray<FormGroup>([]),
  });

  avatar: FormControl = new FormControl('');
  status: FormControl = new FormControl('');
  applicationId = '';

  private getAvatar(userId: string) {
    this.userService.getAvatarSecure(userId).subscribe(response => {
      const blob = new Blob([response], {type: response.type});
      this.avatar = new FormControl(this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob)));
    })
  }

  constructor(protected confirmationService: ConfirmationService,
              protected fb: FormBuilder,
              protected alertService: AlertService,
              protected route: ActivatedRoute,
              protected storage: StorageService,
              protected applicationService: ApplicationService,
              protected userService: UserService,
              protected companyService: CompanyService,
              private sanitizer: DomSanitizer
  ) {
    this.route.params.subscribe(params => {
      this.applicationId = params['applicantId'];
      this.applicationService.getApplication(this.applicationId).subscribe(info => {
        this.mapForm(info.applicant!);
        this.getAvatar(info.applicant!.userId!);
        this.status.setValue(info.state);
      })
    });
  }

  private mapForm(applicantDetailDto: ApplicantDetailDto) {
    this.applicantInfo = this.fb.group({
      id: new FormControl(applicantDetailDto.id),
      name: new FormControl(applicantDetailDto.name),
      email: new FormControl(applicantDetailDto.email),
      lastName: new FormControl(applicantDetailDto.lastName),
      profile: new FormControl(applicantDetailDto.profile),
      phone: new FormControl(applicantDetailDto.phone),
      country: new FormControl(applicantDetailDto.country),
      state: new FormControl(applicantDetailDto.state),
      city: new FormControl(applicantDetailDto.city),
      experiences: this.fb.array(this.buildExperiences(applicantDetailDto)),
    });
  }

  private buildExperiences(applicantDetailDto: ApplicantDetailDto) {
    if (!applicantDetailDto.experiences) {
      return [];
    }
    return applicantDetailDto.experiences.map(exp => {
      return this.fb.group({
        id: new FormControl(exp.id, Validators.required),
        occupation: new FormControl(exp.occupation),
        company: this.fb.group({
          id: new FormControl(exp.company?.id),
          companyName: new FormControl(exp.company?.companyName)
        }),
        dateRange: this.fb.group({
          from: new FormControl(exp.dateRange?.fromDate),
          to: new FormControl(exp.dateRange?.toDate),
        }),
        jobCategories: this.fb.array(this.buildCategories(exp))
      });
    });
  }

  private buildCategories(exp: ExperienceDto): Array<FormGroup> {
    if (!exp.jobCategories) {
      return [];
    }
    return exp.jobCategories.map(cat => {
      return this.fb.group({
        id: new FormControl(cat.id),
        name: new FormControl(cat.name)
      })
    });
  }

  approve() {
    this.changeStatus("APPROVED")
  }

  decline() {
    this.changeStatus("DECLINED")
  }

  private changeStatus(status: StateEnum) {
    this.confirmationService.openConfirm("dialog.confirmation.application." + status, () => {
      this.applicationService.changeApplicationState({state: status}, this.applicationId).subscribe({
        next: response => {
          this.status.setValue(response.state);
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error)
        }
      })
    })

  }
}

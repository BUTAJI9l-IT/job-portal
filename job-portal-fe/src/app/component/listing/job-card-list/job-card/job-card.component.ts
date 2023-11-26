import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {TranslateService} from "@ngx-translate/core";
import {AlertService} from "../../../../service/alert.service";
import {StorageService} from "../../../../service/storage.service";
import {ConfirmationService} from "../../../../service/confirmation.service";
import {JobPositionService} from "../../../../../api/jobPosition.service";
import {JobPositionUpdateDto} from "../../../../../model/jobPositionUpdateDto";
import {JobPositionDto} from "../../../../../model/jobPositionDto";
import {EventData} from "../../../../../model/event.class";
import {EventBusService} from "../../../../service/event-bus.service";
import {UserService} from "../../../../../api/user.service";
import {DomSanitizer} from "@angular/platform-browser";
import {CompanyService} from "../../../../../api/company.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'job-card',
  templateUrl: './job-card.component.html',
  styleUrls: ['./job-card.component.css']
})
export class JobCardComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('avatarImg') avatarImg: ElementRef<HTMLImageElement> | undefined;
  @Output()
  onUpdate = new EventEmitter();
  @Output()
  savedChange = new EventEmitter();
  canEdit = false;
  applicant = false;
  avatar = new FormControl();
  eventBusSub?: Subscription;
  @Input()
  nui: string = ''

  @Input()
  jobPosition: FormGroup = new FormGroup({
    id: new FormControl('', Validators.required),
    company: new FormGroup({
      id: new FormControl(''),
      userId: new FormControl(''),
      companyName: new FormControl('')
    }),
    applied: new FormControl(false),
    favourite: new FormControl(false),
    positionName: new FormControl(''),
    status: new FormControl(JobPositionDto.StatusEnum.ACTIVE),
    jobCategories: new FormArray<FormGroup>([])
  });

  constructor(private translate: TranslateService,
              private alertService: AlertService,
              private storage: StorageService,
              private confirmationService: ConfirmationService,
              private jobService: JobPositionService,
              private companyService: CompanyService,
              private userService: UserService,
              private eventBusService: EventBusService,
              private sanitizer: DomSanitizer) {
    this.eventBusSub = this.eventBusService.on('unsaveJobPosition', (value: { id: string, changedTo: boolean }) => {
      if (value.id! === this.jobPosition.controls['id'].value!) {
        this.jobPosition.controls['favourite'].setValue(value.changedTo!)
      }
    });
  }

  private getAvatar(userId: string) {
    this.userService.getAvatar(userId).subscribe(response => {
      const blob = new Blob([response], {type: response.type});
      this.avatar = new FormControl(this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob)));
    })
  }

  deleteJobPosition() {
    this.updateJobPositionWithStatus(JobPositionUpdateDto.StatusEnum.INACTIVE, 'dialog.confirmation.job.delete');
  }

  restoreJobPosition() {
    this.updateJobPositionWithStatus(JobPositionUpdateDto.StatusEnum.ACTIVE, 'dialog.confirmation.job.restore');
  }

  private updateJobPositionWithStatus(status: JobPositionUpdateDto.StatusEnum, confirmText: string) {
    this.confirmationService.openConfirm(confirmText, () => {
      let id = this.jobPosition.controls['id'].value;
      this.jobService.getJobPosition(id).subscribe({
        next: (response) => {
          this.jobService.updateJobPosition({
            city: response.city,
            contactEmail: response.contactEmail,
            country: response.country,
            detail: response.detail,
            jobCategories: response.jobCategories?.map(cat => cat.id!),
            positionName: response.positionName,
            state: response.state,
            status: status
          }, id).subscribe({
            next: () => {
              this.onUpdate.emit();
            },
            error: err => {
              this.alertService.commonErrorHandle(err.error);
            }
          });
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error);
        }
      })
    });
  }

  ngOnInit(): void {
    let scope = this.storage.getTokenInfo()?.scope!;
    let nui = this.storage.getTokenInfo()?.nui!;
    this.canEdit = (scope === "ADMIN") || (scope === "COMPANY" && nui === this.jobPosition.get("company")?.get("id")!.value)
    this.applicant = (scope === "REGULAR_USER");
    this.nui = nui
  }

  ngOnDestroy(): void {
    if (this.eventBusSub)
      this.eventBusSub.unsubscribe();
  }

  deleteFromFavorite() {
    this.jobService.removeFromFavorites(this.nui, this.jobPosition.get("id")?.value!).subscribe({
      next: () => {
        this.savedChange.emit();
        this.jobPosition.get("favourite")?.setValue(false);
        this.eventBusService.emit(new EventData("unsaveJobPosition", {
          id: this.jobPosition.get('id')?.value!,
          changedTo: false
        }))
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    })
  }

  addToFavorite() {
    this.jobService.addToFavorites(this.nui, this.jobPosition.get("id")?.value!).subscribe({
      next: () => {
        this.savedChange.emit();
        this.eventBusService.emit(new EventData("unsaveJobPosition", {
          id: this.jobPosition.get('id')?.value!,
          changedTo: true
        }))
        this.jobPosition.get("favourite")?.setValue(true);
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    })
  }

  handleIntersection(entries: IntersectionObserverEntry[], observer: IntersectionObserver) {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const imgElement = entry.target;
        this.getAvatar(this.jobPosition.get("company")?.get("userId")!.value);
        observer.unobserve(imgElement);
      }
    })
  }

  ngAfterViewInit(): void {
    const options = {
      root: null,
      rootMargin: '0px',
      threshold: 0.2
    };
    const observer = new IntersectionObserver(this.handleIntersection.bind(this), options);
    observer.observe(this.avatarImg!.nativeElement);
  }
}

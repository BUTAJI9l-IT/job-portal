import {Component, OnDestroy, OnInit} from '@angular/core';
import {StorageService} from "../../../service/storage.service";
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {JobPositionService} from "../../../../api/jobPosition.service";
import {AlertService} from "../../../service/alert.service";
import {EventBusService} from "../../../service/event-bus.service";
import {Subscription} from "rxjs";
import {EventData} from "../../../../model/event.class";
import {UserService} from "../../../../api/user.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-applicant-saved-jobs',
  templateUrl: './applicant-saved-jobs.component.html',
  styleUrls: ['./applicant-saved-jobs.component.css']
})
export class ApplicantSavedJobsComponent implements OnInit, OnDestroy {
  jobPositions = new FormArray<FormGroup>([]);
  eventBusSub?: Subscription;
  applicantId = '';

  constructor(private storage: StorageService, private jobService: JobPositionService, private alertService: AlertService,
              private eventBusService: EventBusService, private userService: UserService, private route: ActivatedRoute) {
    this.eventBusSub = this.eventBusService.on('unsaveJobPosition', (value: { id: string, changedTo: boolean }) => {
      if (!value.changedTo) {
        this.jobPositions = new FormArray(this.jobPositions.controls.filter(jp => jp.get("id")?.value! !== value.id!));
      } else {
        this.loadJobs()
      }
    });
  }

  ngOnDestroy(): void {
    if (this.eventBusSub)
      this.eventBusSub.unsubscribe();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      let userId = params['id'];
      this.userService.getUserInfo(userId).subscribe(info => {
        this.applicantId = info.nui!;
        this.loadJobs();
      })
    })
  }

  private loadJobs() {
    this.jobService.getFavorites(this.applicantId).subscribe({
      next: response => {
        this.jobPositions = new FormArray(response.jobs!.map(jp => {
          return new FormGroup({
            id: new FormControl(jp.id, Validators.required),
            company: new FormGroup({
              id: new FormControl(jp.company?.id),
              companyName: new FormControl(jp.company?.name),
              userId: new FormControl(jp.company?.userId)
            }),
            applied: new FormControl(jp.applied),
            favourite: new FormControl(jp.favourite),
            positionName: new FormControl(jp.positionName),
            status: new FormControl(jp.status),
            jobCategories: new FormArray<FormGroup>(jp.jobCategories?.map(cat => {
              return new FormGroup({
                id: new FormControl(cat.id),
                name: new FormControl(cat.name)
              });
            })!)
          });
        })!)
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error)
      }
    })
  }

  deleteItem(id: string) {
    this.eventBusService.emit(new EventData("unsaveJobPosition", {
      id: id,
      changedTo: false
    }))
  }
}

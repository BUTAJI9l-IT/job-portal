import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {StorageService} from "../../../service/storage.service";
import {JobPositionService} from "../../../../api/jobPosition.service";
import {AlertService} from "../../../service/alert.service";
import {EventBusService} from "../../../service/event-bus.service";
import {EventData} from "../../../../model/event.class";
import {Subscription} from "rxjs";

@Component({
  selector: 'saved-jobs-menu',
  templateUrl: './saved-jobs-menu.component.html',
  styleUrls: ['./saved-jobs-menu.component.css']
})
export class SavedJobsMenuComponent implements OnDestroy, OnInit {
  jobPositions = new FormArray<FormGroup>([]);
  eventBusSub?: Subscription;
  userId = ''
  isApplicant: boolean;

  constructor(private storage: StorageService,
              private jobService: JobPositionService,
              private alertService: AlertService,
              private eventBusService: EventBusService) {
    this.userId = storage.getTokenInfo()?.sub!
    this.isApplicant = storage.getTokenInfo()?.scope === "REGULAR_USER"
  }

  ngOnDestroy(): void {
    if (this.eventBusSub)
      this.eventBusSub.unsubscribe();
  }

  ngOnInit(): void {
    this.eventBusSub = this.eventBusService.on('unsaveJobPosition', (value: { id: string, changedTo: boolean }) => {
      if (!value.changedTo) {
        this.jobPositions = new FormArray(this.jobPositions.controls.filter(jp => jp.get("id")?.value! !== value.id!));
      } else {
        this.loadJobs()
      }
    });
    this.loadJobs();
  }

  private loadJobs() {
    if (this.isApplicant) {
      this.jobService.getFavorites(this.storage.getTokenInfo()?.nui!).subscribe({
        next: response => {
          this.jobPositions = new FormArray(response.jobs!.map(jp => {
            return new FormGroup({
              id: new FormControl(jp.id, Validators.required),
              company: new FormGroup({
                id: new FormControl(jp.company?.id),
                companyName: new FormControl(jp.company?.name)
              }),
              positionName: new FormControl(jp.positionName)
            });
          })!)
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error)
        }
      })
    }
  }

  deleteFromFavorite(id: string) {
    this.jobService.removeFromFavorites(this.storage.getTokenInfo()?.nui!, id).subscribe({
      next: () => {
        this.eventBusService.emit(new EventData("unsaveJobPosition", {
          id: id,
          changedTo: false
        }))
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    })
  }
}

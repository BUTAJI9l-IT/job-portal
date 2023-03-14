import {Component} from '@angular/core';
import {FormControl} from "@angular/forms";
import {UserService} from "../../../../api/user.service";
import {AlertService} from "../../../service/alert.service";
import {ChangePasswordComponent} from "../../../component/dialog/change-password/change-password.component";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmationService} from "../../../service/confirmation.service";
import {EventBusService} from "../../../service/event-bus.service";
import {EventData} from "../../../../model/event.class";
import {ActivatedRoute} from "@angular/router";
import {StorageService} from "../../../service/storage.service";

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.css']
})
export class UserSettingsComponent {
  notificationsToggle = new FormControl(false);
  sub: string = ''

  constructor(private userService: UserService,
              private storage: StorageService,
              private alertService: AlertService,
              private dialog: MatDialog,
              private confirmationService: ConfirmationService,
              private eventBusService: EventBusService, private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      const sub = params['id'];
      this.sub = sub;
      this.userService.getUserPreferences(sub).subscribe({
        next: preferences => {
          this.notificationsToggle.setValue(preferences.notificationsEnabled);
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error)
        }
      });
      this.notificationsToggle.valueChanges.subscribe(value => {
        let uid = sub;
        this.userService.getUserPreferences(uid).subscribe({
          next: preferences => {
            this.userService.updatePreferences({...preferences, notificationsEnabled: value!}, uid).subscribe({
              next: () => {
              },
              error: err => {
                this.alertService.commonErrorHandle(err.error)
              }
            })
          },
          error: err => {
            this.alertService.commonErrorHandle(err.error)
          }
        })
      })
    });
  }

  deleteAccount() {
    this.confirmationService.openConfirm('dialog.confirmation.settings.delete', () => {
      this.userService.deleteUser(this.sub).subscribe({
        next: () => {
          window.location.replace('');
          if (this.sub === this.storage.getTokenInfo()?.sub!) {
            this.eventBusService.emit(new EventData('logout', null));
          }
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error)
        }
      });
    });
  }

  changePassword() {
    this.dialog.open(ChangePasswordComponent, {data: {userId: this.sub}})
  }
}

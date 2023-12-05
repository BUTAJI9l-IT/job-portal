import {Component, Input} from '@angular/core';
import {UserService} from "../../../../api/user.service";
import {FormControl} from "@angular/forms";
import {AlertService} from "../../../service/alert.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'avatar-menu',
  templateUrl: './avatar-menu.component.html',
  styleUrls: ['./avatar-menu.component.css']
})
export class AvatarMenuComponent {
  @Input()
  userId: string = '';
  @Input()
  avatarFormControl: FormControl = new FormControl();

  constructor(private userService: UserService, private alertService: AlertService,
              private sanitizer: DomSanitizer) {
  }

  uploadNewAvatar(imgFile: any) {
    if (imgFile.target.files && imgFile.target.files[0]) {
      const file = imgFile.target.files[0];
      if (file.size > 128000) {
        this.alertService.showMessage("inputs.file.avatar.error.size");
      } else {
        this.userService.uploadAvatarForm(this.userId, file).subscribe({
          next: response => {
            const blob = new Blob([response], {type: response.type});
            this.avatarFormControl.setValue(this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob)));
          },
          error: err => {
            this.alertService.commonErrorHandle(err.error);
          }
        });
      }
    }
  }

  deleteAvatar() {
    this.userService.deleteAvatar(this.userId).subscribe({
      next: response => {
        const blob = new Blob([response], {type: response.type});
        this.avatarFormControl.setValue(this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob)));
      },
      error: err => {
        this.alertService.commonErrorHandle(err.error);
      }
    });
  }

}

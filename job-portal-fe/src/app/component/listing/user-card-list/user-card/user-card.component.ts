import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {UserService} from "../../../../../api/user.service";
import {DomSanitizer} from "@angular/platform-browser";
import {ConfirmationService} from "../../../../service/confirmation.service";
import {AlertService} from "../../../../service/alert.service";

@Component({
  selector: 'user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent {
  @Output()
  onUpdate = new EventEmitter();
  @ViewChild('avatarImg') avatarImg: ElementRef<HTMLImageElement> | undefined;
  avatar = new FormControl();

  @Input()
  user: FormGroup = new FormGroup({
    id: new FormControl(''),
    email: new FormControl(''),
    lastName: new FormControl(''),
    name: new FormControl(''),
    scope: new FormControl('')
  });

  constructor(
    private userService: UserService, private confirmationService: ConfirmationService,
    private sanitizer: DomSanitizer, private alertService: AlertService) {
  }

  private getAvatar(userId: string) {
    this.userService.getAvatar(userId).subscribe(response => {
      const blob = new Blob([response], {type: response.type});
      this.avatar = new FormControl(this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob)));
    })
  }

  handleIntersection(entries: IntersectionObserverEntry[], observer: IntersectionObserver) {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const imgElement = entry.target;
        this.getAvatar(this.user.get("id")!.value);
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

  deleteUser() {
    this.confirmationService.openConfirm('dialog.confirmation.users.delete', () => {
      this.userService.deleteUser(this.user.get("id")?.value!).subscribe({
        next: () => {
          this.onUpdate.emit();
        },
        error: err => {
          this.alertService.commonErrorHandle(err.error)
        }
      });
    });
  }
}

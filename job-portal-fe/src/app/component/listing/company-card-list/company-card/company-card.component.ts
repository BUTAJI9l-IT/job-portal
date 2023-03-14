import {AfterViewInit, Component, ElementRef, Input, ViewChild} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {UserService} from "../../../../../api/user.service";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'company-card',
  templateUrl: './company-card.component.html',
  styleUrls: ['./company-card.component.css']
})
export class CompanyCardComponent implements AfterViewInit {
  @ViewChild('avatarImg') avatarImg: ElementRef<HTMLImageElement> | undefined;
  avatar = new FormControl();

  @Input()
  company: FormGroup = new FormGroup({
    id: new FormControl(''),
    companyName: new FormControl(''),
    userId: new FormControl('')
  });

  constructor(
    private userService: UserService,
    private sanitizer: DomSanitizer) {
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
        this.getAvatar(this.company.get("userId")!.value);
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

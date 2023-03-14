import {Component} from '@angular/core';
import {FormArray, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../../../../api/user.service";
import {DomSanitizer} from "@angular/platform-browser";
import {CompanyDetailDto} from "../../../../model/companyDetailDto";
import {CompanyDto} from "../../../../model/companyDto";
import {CompanyService} from "../../../../api/company.service";
import CompanySizeEnum = CompanyDto.CompanySizeEnum;

@Component({
  selector: 'app-company-detail',
  templateUrl: './company-detail.component.html',
  styleUrls: ['./company-detail.component.css']
})
export class CompanyDetailComponent {
  avatar: FormControl = new FormControl('');
  company: any = new FormGroup({
    id: new FormControl('', Validators.required),
    companyName: new FormControl('', Validators.required),
    description: new FormControl(''),
    companyLink: new FormControl(''),
    userId: new FormControl(''),
    jobPositions: new FormArray([]),
    companySize: new FormControl<CompanySizeEnum | undefined>(undefined)
  });

  constructor(
    private route: ActivatedRoute,
    private companyService: CompanyService,
    private userService: UserService,
    private sanitizer: DomSanitizer,
  ) {

  }

  private getAvatar(userId: string) {
    this.userService.getAvatar(userId).subscribe(response => {
      const blob = new Blob([response], {type: response.type});
      this.avatar = new FormControl(this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(blob)));
    })
  }

  private buildCompany(company: CompanyDetailDto): void {
    this.company = new FormGroup({
      id: new FormControl(company.id, Validators.required),
      userId: new FormControl(company.userId, Validators.required),
      companyName: new FormControl(company.companyName, Validators.required),
      companyLink: new FormControl(company.companyLink, Validators.required),
      description: new FormControl(company.description),
      companySize: new FormControl<CompanySizeEnum | undefined>(company.companySize)
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      let companyId = params['id'];
      this.companyService.getCompany(companyId).subscribe(comp => {
        this.buildCompany(comp);
        this.getAvatar(comp.userId!);
      })
    });
  }

}

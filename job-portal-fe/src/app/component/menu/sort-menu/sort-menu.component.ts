import {Component, Input} from '@angular/core';
import {FormControl} from "@angular/forms";

@Component({
  selector: 'sort-menu',
  templateUrl: './sort-menu.component.html',
  styleUrls: ['./sort-menu.component.css']
})
export class SortMenuComponent {
  // COUNTRY STATE CITY APPLICATION_DATE NAME COMPANY_SIZE EMAIL JOB_POSITION APPLICANT COMPANY CATEGORY
  @Input()
  country = false;
  @Input()
  state = false;
  @Input()
  city = false;
  @Input()
  applicationDate = false;
  @Input()
  name = false;
  @Input()
  companySize = false;
  @Input()
  email = false;
  @Input()
  applicantName = false;
  @Input()
  companyName = false;
  @Input()
  category = false;
  @Input()
  relevance = false;
  @Input()
  sortArray = new FormControl<string[]>([]);

}

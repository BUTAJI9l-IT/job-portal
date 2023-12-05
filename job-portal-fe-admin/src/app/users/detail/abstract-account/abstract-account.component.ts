import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {DomSanitizer} from '@angular/platform-browser';
import {AlertService} from "../../../service/alert.service";
import {StorageService} from "../../../service/storage.service";
import {UserService} from "../../../../api/user.service";
import {ApplicantService} from "../../../../api/applicant.service";
import {CompanyService} from "../../../../api/company.service";
import {ConfirmationService} from "../../../service/confirmation.service";
import {UserDto} from "../../../../model/userDto";
import {emailValidators} from "../../../component/input/email/email.component";

@Component({template: ''})
export class AbstractAccountComponent {
  userInfo: FormGroup = new FormGroup({
    id: new FormControl(''),
    email: new FormControl(''),
    scope: new FormControl(''),
    name: new FormControl(''),
    lastName: new FormControl('')
  });
  avatar: FormControl = new FormControl('');
  isCompany: boolean = false;
  editMode: boolean = false;
  hasChange: boolean = false;

  initialValueUser!: any;

  initialValueNonUser!: any;
  nui: string = '';


  protected buildNonUserForm(): void {
  }

  protected getNonUserForm(): FormGroup {
    return new FormGroup({});
  }

  protected update(): void {
  }

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
              private storage: StorageService,
              protected userService: UserService,
              protected applicantService: ApplicantService,
              protected companyService: CompanyService,
              private sanitizer: DomSanitizer
  ) {
    this.route.params.subscribe(params => {
      let userId = params['id'];
      this.userService.getUserInfo(userId).subscribe(info => {
        this.buildUserForm(info);
        this.nui = info.nui!;
        this.buildNonUserForm();
      })
      this.getAvatar(userId);
    });
  }


  private buildUserForm(userDto: UserDto): void {
    this.userInfo = this.fb.group({
      id: new FormControl(userDto.id, [Validators.required]),
      email: new FormControl(userDto.email, emailValidators),
      scope: new FormControl(userDto.scope, [Validators.required]),
      name: new FormControl(userDto.name),
      lastName: new FormControl(userDto.lastName),
    });
    this.isCompany = this.userInfo.value.scope === "COMPANY";

    this.userInfo.controls["id"].disable();
    this.userInfo.controls["email"].disable();
    this.userInfo.controls["scope"].disable();
  }

  protected patchUserName() {
    this.userService.getUserInfo(this.userInfo.controls["id"].value).subscribe(info => {
      this.userInfo.controls["name"].setValue(info.name);
      this.userInfo.controls["lastName"].setValue(info.lastName);
    })
  }

  protected updateUser() {
    if (this.userInfo.invalid || this.getNonUserForm().invalid) {
      this.markDirty();
      return;
    }

    if (this.hasChange) {
      this.update();
    }

    this.changeEditMode();
    this.initValues();
  }

  protected safeCancelChange() {
    if (this.hasChange) {
      this.confirmationService.openConfirm("dialog.confirmation.text.changes", () => {
        this.rollbackChanges();
        this.changeEditMode();
      });
    } else {
      this.changeEditMode();
    }
  }

  private markDirty() {
    for (let inner in this.userInfo.controls) {
      this.userInfo.get(inner)?.markAsDirty();
    }
    for (let inner in this.getNonUserForm().controls) {
      this.getNonUserForm().get(inner)?.markAsDirty();
    }
  }

  private rollbackChanges() {
    this.getNonUserForm().setValue(this.initialValueNonUser);
    this.userInfo.patchValue(this.initialValueUser);
    this.initValues();
  }

  private changeEditMode() {
    this.editMode = !this.editMode;
    if (this.editMode) {
      this.initValues();
    }
  }

  protected initValues() {
    this.hasChange = false;
    this.initialValueNonUser = this.getNonUserForm().value;
    this.initialValueUser = this.userInfo.value;
    this.getNonUserForm().valueChanges.subscribe(() => {
      this.hasChange = this.compareWithInitial();
    });
    this.userInfo.valueChanges.subscribe(() => {
      this.hasChange = this.compareWithInitial();
    });
  }

  private compareWithInitial() {
    return Object.keys(this.initialValueNonUser).some(key => this.getNonUserForm().value[key] !=
      this.initialValueNonUser[key]) || Object.keys(this.initialValueUser).some(key => this.userInfo.value[key] !=
      this.initialValueUser[key]);
  }

}

import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
import {MatIconRegistry} from "@angular/material/icon";
import {DomSanitizer} from "@angular/platform-browser";
import {MatDialog} from "@angular/material/dialog";
import {LoginComponent} from "./home/components/login/login.component";
import {Subscription} from "rxjs";
import {StorageService} from "./service/storage.service";
import {EventBusService} from "./service/event-bus.service";
import {JwtClaims} from "../model/jwtClaims";
import {TranslateService} from "@ngx-translate/core";
import {getLocaleId} from "@angular/common";
import {UserService} from "../api/user.service";
import {LoadingService} from "./service/loading.service";
import ScopeEnum = JwtClaims.ScopeEnum;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy, AfterViewInit {
  private scope: JwtClaims.ScopeEnum | undefined;
  isCompany = false;
  isAdmin = false;
  isLoggedIn = false;
  showAdminBoard = false;
  username?: string;
  eventBusSub?: Subscription;
  userId: string | undefined;
  nonUserId: string | undefined;
  userLang: string | undefined;
  isLoading: boolean = false;

  constructor(
    private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer,
    public dialog: MatDialog,
    private storageService: StorageService,
    private eventBusService: EventBusService,
    private translate: TranslateService,
    private loading: LoadingService,
    private userService: UserService) {
    this.matIconRegistry.addSvgIcon("jp_icon",
      this.domSanitizer.bypassSecurityTrustResourceUrl("assets/jp_icon.svg"), {}
    );
    this.resolveLanguage();
  }

  ngAfterViewInit() {
    this.loading.isLoading().subscribe(isLoading => {
      setTimeout(() => {
        this.isLoading = isLoading;
      });
    });
  }

  private resolveLanguage() {
    if (this.isLoggedIn && this.userLang) {
      this.translate.setDefaultLang(this.userLang);
      this.translate.use(this.userLang);
    } else {
      if (this.translate.langs.includes(getLocaleId(navigator.language))) {
        this.translate.setDefaultLang(getLocaleId(navigator.language));
      } else {
        this.translate.setDefaultLang('en')
      }
      this.translate.use('en');
    }
  }

  openLoginDialog(): void {
    const dialogRef = this.dialog.open(LoginComponent);
  }

  logout(): void {
    this.storageService.clean();

    this.isLoggedIn = false;
    this.scope = undefined;
    this.showAdminBoard = false;
    this.username = undefined;
    this.userId = undefined;
    this.nonUserId = undefined;
  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {
      const info = this.storageService.getTokenInfo();
      this.scope = info?.scope;
      this.isCompany = this.scope === JwtClaims.ScopeEnum.COMPANY;
      this.isAdmin = this.scope === JwtClaims.ScopeEnum.ADMIN;
      this.showAdminBoard = this.scope === ScopeEnum.ADMIN;
      this.username = info?.email;
      this.userId = info?.sub!;
      this.nonUserId = info?.nui;

      this.userService.getUserPreferences(this.userId).subscribe({
          next: response => {
            this.userLang = response.language;
            this.resolveLanguage();
          },
          error: () => {
            this.userLang = info?.lang ? info?.lang : 'en';
            this.resolveLanguage();
          }
        }
      )
    }
    this.eventBusSub = this.eventBusService.on('logout', () => {
      this.logout();
      window.location.replace('');
    });
  }

  ngOnDestroy(): void {
    if (this.eventBusSub)
      this.eventBusSub.unsubscribe();
  }
}

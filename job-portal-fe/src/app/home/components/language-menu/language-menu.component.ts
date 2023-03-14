import {Component, Input} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {UserService} from "../../../../api/user.service";
import {PreferencesDto} from "../../../../model/preferencesDto";

@Component({
  selector: 'language-menu',
  templateUrl: './language-menu.component.html',
  styleUrls: ['./language-menu.component.css']
})
export class LanguageMenuComponent {
  open: boolean = false;
  @Input()
  userId: string | undefined;
  constructor(private translate: TranslateService, private userService: UserService) {
  }

  useLanguage(language: string): void {
    this.translate.use(language);
    if (this.userId) {
      this.translate.setDefaultLang(language);
      this.userService.getUserPreferences(this.userId).subscribe({
        next: (response: PreferencesDto) => {
          this.userService.updatePreferences({...response, language: language}, this.userId!).subscribe();
        }
      })
    }
  }
}

import {Injector} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {LOCATION_INITIALIZED} from '@angular/common';
import {TranslateService} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient);
}

export function ApplicationInitializerFactory(
  translate: TranslateService, injector: Injector) {
  return async () => {
    await injector.get(LOCATION_INITIALIZED, Promise.resolve(null));

    const deaultLang = 'en';
    translate.addLangs(['en', 'cs', 'ru', 'sk']);
    translate.setDefaultLang(deaultLang);
    try {
      await translate.use(deaultLang).toPromise();
    } catch (err) {
      console.log(err);
    }
    console.log(`Successfully initialized ${deaultLang} language.`);
  };
}

import {MatPaginatorIntl} from "@angular/material/paginator";
import {TranslateService} from "@ngx-translate/core";

export class LocalizedPaginator {
  constructor(private readonly translation: TranslateService) {
  }

  getPaginatorIntl(): MatPaginatorIntl {
    const paginatorIntl = new MatPaginatorIntl();
    this.translation.stream('paginator.paginatorIntl').subscribe(dict => {
      Object.assign(paginatorIntl, dict);
      paginatorIntl.changes.next();
    });
    const originalGetRangeLabel = paginatorIntl.getRangeLabel;
    paginatorIntl.getRangeLabel = (page: number, size: number, len: number) => {
      return originalGetRangeLabel(page, size, len)
        .replace('of', this.translation.instant('paginator.of'));
    };
    return paginatorIntl;
  }
}

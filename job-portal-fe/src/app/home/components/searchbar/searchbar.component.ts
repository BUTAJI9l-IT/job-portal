import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {AfterViewInit, ChangeDetectorRef, Component, EventEmitter, Input} from '@angular/core';
import {MatChipEditedEvent, MatChipInputEvent} from '@angular/material/chips';
import {FormControl} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.css']
})
export class SearchbarComponent implements AfterViewInit {
  changeInput = new EventEmitter<void>();
  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  @Input()
  tags = new FormControl(['Programmer', 'Full-Time', 'Masaryk University']);
  @Input()
  inputQ = new FormControl('');
  @Input()
  routerLinkSearch = '';

  constructor(private route: ActivatedRoute, private cdr: ChangeDetectorRef) {
  }


  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();

    if (value) {
      let arr = this.tags.value!
      arr.push(value)
      this.tags.setValue(arr);
    }

    event.chipInput!.clear();
    this.inputQ.setValue('');
  }

  remove(tag: string): void {
    const index = this.tags.value!.indexOf(tag);

    if (index >= 0) {
      let arr = this.tags.value!
      arr.splice(index, 1)
      this.tags.setValue(arr);
    }
  }

  edit(tag: string, event: MatChipEditedEvent) {
    const value = event.value.trim();

    // Remove fruit if it no longer has a name
    if (!value) {
      this.remove(tag);
      return;
    }

    // Edit existing fruit
    const index = this.tags.value!.indexOf(tag);
    if (index >= 0) {
      let arr = this.tags.value!
      arr[index] = value;
      this.tags.setValue(arr);
    }
  }


  ngAfterViewInit(): void {
    this.route.queryParams
      .subscribe(params => {
          this.tags.setValue([].concat(params['q'] ? params['q'] : []));
        }
      );
    this.cdr.detectChanges();
  }
}

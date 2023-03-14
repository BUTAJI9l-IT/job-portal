import {animate, state, style, transition, trigger} from "@angular/animations";

export const slideInOut = trigger('slideInOut', [
  state('in', style({
    overflow: 'hidden',
    height: '*',
  })),
  state('out', style({
    opacity: '0',
    overflow: 'hidden',
    height: '0px',
  })),
  transition('in => out', animate('400ms ease-in-out')),
  transition('out => in', animate('400ms ease-in-out'))
]);

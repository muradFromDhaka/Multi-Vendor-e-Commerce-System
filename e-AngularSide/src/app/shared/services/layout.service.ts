import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {

  private sidebarState = new BehaviorSubject<boolean>(true);

  readonly sidebarState$ = this.sidebarState.asObservable();

  toggleSidebar(): void {
    this.sidebarState.next(!this.sidebarState.value);
  }

  openSidebar(): void {
    this.sidebarState.next(true);
  }

  closeSidebar(): void {
    this.sidebarState.next(false);
  }

}
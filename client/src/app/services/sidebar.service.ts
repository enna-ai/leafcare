import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SidebarService {
  private selectedFilterSubject: BehaviorSubject<{ status: string | null, category: string | null }> = new BehaviorSubject<{ status: string | null, category: string | null }>({ status: null, category: null });
  public selectedFilter$: Observable<{ status: string | null, category: string | null }> = this.selectedFilterSubject.asObservable();

  private selectedItem: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);
  selectedItem$: Observable<string | null> = this.selectedItem.asObservable();

  constructor() { }

  getSelectedItem(): string | null {
    return this.selectedItem.value;
  }

  setSelectedItem(value: string | null) {
    this.selectedItem.next(value);
  }

  setSelectedFilter(filter: { status: string | null, category: string | null }) {
    this.selectedFilterSubject.next(filter);
  }

  getSelectedFilter(): { status: string | null, category: string | null } {
    return this.selectedFilterSubject.getValue();
  }
}

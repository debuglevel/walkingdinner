import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

import { Organisation } from '../organisation';
import { OrganisationService } from '../organisation.service';

@Component({
  selector: 'app-organisation-search',
  templateUrl: './organisation-search.component.html',
  styleUrls: ['./organisation-search.component.css'],
})
export class OrganisationSearchComponent implements OnInit {
  organisations$: Observable<Organisation[]> | undefined;
  private searchTerms = new Subject<string>();

  constructor(private organisationService: OrganisationService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.organisations$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) =>
        this.organisationService.searchOrganisations(term)
      )
    );
  }
}

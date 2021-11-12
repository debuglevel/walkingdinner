import { Component, OnInit } from '@angular/core';

import { Organisation } from '../organisation';
import { OrganisationService } from '../organisation.service';

@Component({
  selector: 'app-organisations',
  templateUrl: './organisations.component.html',
  styleUrls: ['./organisations.component.css'],
})
export class OrganisationsComponent implements OnInit {
  organisations: Organisation[] | undefined;

  constructor(private organisationService: OrganisationService) {}

  ngOnInit() {
    this.getOrganisations();
  }

  getOrganisations(): void {
    this.organisationService
      .getOrganisations()
      .subscribe((organisations) => (this.organisations = organisations));
  }

  add(name: string, mail: string): void {
    name = name.trim();
    if (!name) {
      return;
    }
    this.organisationService
      .addOrganisation({ name, mail } as Organisation)
      .subscribe((organisation) => {
        if (this.organisations) {
          this.organisations.push(organisation);
        }
      });
  }

  delete(organisation: Organisation): void {
    if (this.organisations) {
      this.organisations = this.organisations.filter((h) => h !== organisation);
      this.organisationService.deleteOrganisation(organisation).subscribe();
    }
  }
}

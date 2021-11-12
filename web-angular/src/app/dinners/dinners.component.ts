import { Component, OnInit } from '@angular/core';

import { Dinner } from '../dinner';
import { DinnerService } from '../dinner.service';
import { OrganisationService } from '../organisation.service';
import { Organisation } from '../organisation';

@Component({
  selector: 'app-dinners',
  templateUrl: './dinners.component.html',
  styleUrls: ['./dinners.component.css'],
})
export class DinnersComponent implements OnInit {
  dinners: Dinner[] | undefined;
  organisations: Organisation[] | undefined;

  constructor(
    private dinnerService: DinnerService,
    private organisationService: OrganisationService
  ) {}

  ngOnInit() {
    this.getDinners();
    this.getOrganisations();
  }

  getDinners(): void {
    this.dinnerService
      .getDinners()
      .subscribe((dinners) => (this.dinners = dinners));
  }

  getOrganisations(): void {
    this.organisationService
      .getOrganisations()
      .subscribe((organisations) => (this.organisations = organisations));
  }

  add(name: string, begin: string, city: string, organisationId: string): void {
    name = name.trim();
    if (!name) {
      return;
    }
    const id = null;

    this.dinnerService
      .addDinner({ id, name, begin, city, organisationId } as Dinner)
      .subscribe((dinner) => {
        if (this.dinners) {
          this.dinners.push(dinner);
        }
      });
  }

  delete(dinner: Dinner): void {
    if (this.dinners) {
      this.dinners = this.dinners.filter((h) => h !== dinner);
      this.dinnerService.deleteDinner(dinner).subscribe();
    }
  }
}

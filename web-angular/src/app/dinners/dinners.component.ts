import { Component, OnInit } from "@angular/core";

import { Dinner } from "../dinner";
import { DinnerService } from "../dinner.service";
import { OrganisationService } from "../organisation.service";
import { Organisation } from '../organisation';

@Component({
  selector: "app-dinners",
  templateUrl: "./dinners.component.html",
  styleUrls: ["./dinners.component.css"],
})
export class DinnersComponent implements OnInit {
  dinners: Dinner[];
  organisations: Organisation[];

  constructor(private dinnerService: DinnerService,
    private organisationService: OrganisationService) {}

  ngOnInit() {
    this.getDinners();
    this.getOrganisations();
  }

  getDinners(): void {
    this.dinnerService
      .getDinners()
      .subscribe(dinners => (this.dinners = dinners));
  }

  getOrganisations(): void {
    this.organisationService
      .getOrganisations()
      .subscribe(organisations => (this.organisations = organisations));
  }

  add(name: string, begin: string, city: string): void {
    name = name.trim();
    if (!name) {
      return;
    }
    const id = null;

    this.dinnerService
      .addDinner({ id, name, begin, city } as Dinner)
      .subscribe(dinner => {
        this.dinners.push(dinner);
      });
  }

  delete(dinner: Dinner): void {
    this.dinners = this.dinners.filter(h => h !== dinner);
    this.dinnerService.deleteDinner(dinner).subscribe();
  }
}

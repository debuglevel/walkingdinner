import { Component, OnInit, Input } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Location } from "@angular/common";

import { Dinner } from "../dinner";
import { DinnerService } from "../dinner.service";
import { OrganisationService } from "../organisation.service";
import { Organisation } from "../organisation";

@Component({
  selector: "app-dinner-detail",
  templateUrl: "./dinner-detail.component.html",
  styleUrls: ["./dinner-detail.component.css"],
})
export class DinnerDetailComponent implements OnInit {
  @Input() dinner: Dinner;
  organisations: Organisation[];

  constructor(
    private route: ActivatedRoute,
    private dinnerService: DinnerService,
    private organisationService: OrganisationService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getDinner();
    this.getOrganisations();
  }

  getDinner(): void {
    const id = this.route.snapshot.paramMap.get("id");
    this.dinnerService
      .getDinner(id)
      .subscribe(dinner => (this.dinner = dinner));
  }

  getOrganisations(): void {
    this.organisationService
      .getOrganisations()
      .subscribe(organisations => (this.organisations = organisations));
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.dinnerService.updateDinner(this.dinner).subscribe(() => this.goBack());
  }
}

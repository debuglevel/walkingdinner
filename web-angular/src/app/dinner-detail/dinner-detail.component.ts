import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Dinner } from '../dinner';
import { DinnerService } from '../dinner.service';
import { OrganisationService } from '../organisation.service';
import { Organisation } from '../organisation';
import { CalculationService } from '../calculation.service';
import { Calculation } from '../calculation';

@Component({
  selector: 'app-dinner-detail',
  templateUrl: './dinner-detail.component.html',
  styleUrls: ['./dinner-detail.component.css'],
})
export class DinnerDetailComponent implements OnInit {
  @Input() dinner: Dinner | undefined;
  organisations: Organisation[] | undefined;

  constructor(
    private route: ActivatedRoute,
    private dinnerService: DinnerService,
    private organisationService: OrganisationService,
    private calculationService: CalculationService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getDinner();
    this.getOrganisations();
  }

  getDinner(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.dinnerService
        .getDinner(id)
        .subscribe((dinner) => (this.dinner = dinner));
    }
  }

  getOrganisations(): void {
    this.organisationService
      .getOrganisations()
      .subscribe((organisations) => (this.organisations = organisations));
  }

  calculatePlan(): void {
    if (this.dinner) {
      const dinnerId = this.dinner.id;

      // TODO: do not hardcode these values
      this.calculationService
        .addCalculation({
          finished: false,
          dinnerId,
          populationSize: 200,
          fitnessThreshold: 0.001,
          steadyFitness: 100,
        } as Calculation)
        .subscribe((calculation) => {
          // do nothing
          //this.calculations.push(calculation);
        });
    }
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.dinner) {
      this.dinnerService
        .updateDinner(this.dinner)
        .subscribe(() => this.goBack());
    }
  }
}

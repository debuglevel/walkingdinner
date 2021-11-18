import { Component, OnInit } from '@angular/core';

import { Team } from '../team';
import { TeamService } from '../team.service';
import { Dinner } from '../dinner';
import { DinnerService } from '../dinner.service';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css'],
})
export class TeamsComponent implements OnInit {
  teams: Team[] | undefined;
  dinners: Dinner[] | undefined;

  constructor(
    private teamService: TeamService,
    private dinnerService: DinnerService
  ) {}

  ngOnInit() {
    this.getTeams();
    this.getDinners();
  }

  getTeams(): void {
    this.teamService.getTeams().subscribe((teams) => (this.teams = teams));
  }

  getDinners(): void {
    this.dinnerService
      .getDinners()
      .subscribe((dinners) => (this.dinners = dinners));
  }

  add(
    address: string,
    name1: string,
    name2: string,
    mail1: string,
    mail2: string,
    phone1: string,
    phone2: string,
    city: string,
    notes: string,
    veganAppetizer: boolean,
    veganMaindish: boolean,
    veganDessert: boolean,
    vegetarianAppetizer: boolean,
    vegetarianMaindish: boolean,
    vegetarianDessert: boolean,
    omnivoreAppetizer: boolean,
    omnivoreMaindish: boolean,
    omnivoreDessert: boolean,
    diet: string,
    dinnerId: string
  ): void {
    //name = name.trim();
    // if (!name) {
    //   return;
    // }

    console.log(veganAppetizer);

    this.teamService
      .addTeam({
        address,
        name1,
        name2,
        mail1,
        mail2,
        phone1,
        phone2,
        city,
        notes,
        veganAppetizer,
        veganMaindish,
        veganDessert,
        vegetarianAppetizer,
        vegetarianMaindish,
        vegetarianDessert,
        omnivoreAppetizer,
        omnivoreMaindish,
        omnivoreDessert,
        diet,
        dinnerId,
      } as Team)
      .subscribe((team) => {
        if (this.teams) {
          this.teams.push(team);
        }
      });
  }

  delete(team: Team): void {
    if (this.teams) {
      this.teams = this.teams.filter((h) => h !== team);
      this.teamService.deleteTeam(team).subscribe();
    }
  }
}

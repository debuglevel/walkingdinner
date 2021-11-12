import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Team } from '../team';
import { TeamService } from '../team.service';

@Component({
  selector: 'app-team-detail',
  templateUrl: './team-detail.component.html',
  styleUrls: ['./team-detail.component.css'],
})
export class TeamDetailComponent implements OnInit {
  @Input() team: Team | undefined;

  constructor(
    private route: ActivatedRoute,
    private teamService: TeamService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getTeam();
  }

  getTeam(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.teamService.getTeam(id).subscribe((team) => (this.team = team));
    }
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.team) {
      this.teamService.updateTeam(this.team).subscribe(() => this.goBack());
    }
  }
}

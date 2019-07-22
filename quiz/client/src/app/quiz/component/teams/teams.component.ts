import {Component, OnInit} from '@angular/core';
import {Subscribable} from '../../../common/service/subscribable';
import {TeamService} from '../../service/team.service';
import {Observable} from 'rxjs';
import {Team} from '../../model/team';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent extends Subscribable implements OnInit {

  teams$: Observable<Team[]>;
  teams: Team[];

  constructor(
    private teamService: TeamService
  ) {
    super();
  }

  ngOnInit() {
    this.teams$ = this.teamService.teams();
  }

}

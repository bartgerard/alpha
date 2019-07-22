import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TeamComponent} from './component/team/team.component';
import {TeamsComponent} from './component/teams/teams.component';
import {HttpClientModule} from '@angular/common/http';


@NgModule({
  declarations: [
    TeamComponent,
    TeamsComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule
  ]
})
export class QuizModule {
}

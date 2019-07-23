import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TeamComponent} from './component/team/team.component';
import {TeamsComponent} from './component/teams/teams.component';
import {HttpClientModule} from '@angular/common/http';
import { QuizComponent } from './component/quiz/quiz.component';


@NgModule({
  declarations: [
    TeamComponent,
    TeamsComponent,
    QuizComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule
  ]
})
export class QuizModule {
}

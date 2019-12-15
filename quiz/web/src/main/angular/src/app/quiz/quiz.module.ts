import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TeamComponent} from './component/team/team.component';
import {TeamsComponent} from './component/teams/teams.component';
import {HttpClientModule} from '@angular/common/http';
import {QuizComponent} from './component/quiz/quiz.component';
import {QuestionComponent} from './component/question/question.component';


@NgModule({
  declarations: [
    TeamComponent,
    TeamsComponent,
    QuizComponent,
    QuestionComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule
  ]
})
export class QuizModule {
}

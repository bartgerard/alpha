import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TeamComponent} from './component/team/team.component';
import {TeamsComponent} from './component/teams/teams.component';
import {HttpClientModule} from '@angular/common/http';
import {QuizComponent} from './component/quiz/quiz.component';
import {QuestionComponent} from './component/question/question.component';
import {ButtonModule, InputTextModule} from 'primeng';
import {AnswerComponent} from './component/answer/answer.component';
import {FormsModule} from '@angular/forms';


@NgModule({
  declarations: [
    TeamComponent,
    TeamsComponent,
    QuizComponent,
    QuestionComponent,
    AnswerComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    InputTextModule,
    FormsModule,
    ButtonModule
  ]
})
export class QuizModule {
}

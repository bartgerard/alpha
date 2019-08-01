import {Component, OnInit} from '@angular/core';
import {QuizService} from '../../service/quiz.service';
import {Subscribable} from '../../../common/service/subscribable';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.css']
})
export class QuizComponent extends Subscribable implements OnInit {

  data: string;

  constructor(
    private _quizService: QuizService
  ) {
    super();
  }

  ngOnInit() {
    this.subs.sink = this._quizService.events()
      .subscribe(event => this.data = event);
  }

}

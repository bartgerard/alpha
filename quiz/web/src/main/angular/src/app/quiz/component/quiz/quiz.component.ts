import {Component, OnInit} from '@angular/core';
import {QuizService} from '../../service/quiz.service';
import {Subscribable} from '../../../common/service/subscribable';
import {StateChanged} from '../../model/state-changed';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.css']
})
export class QuizComponent extends Subscribable implements OnInit {

  data: string;
  state: StateChanged;

  constructor(
    private _quizService: QuizService
  ) {
    super();
  }

  ngOnInit() {
    this.subs.sink = this._quizService.events()
      .subscribe(event => {
        this.data = `${event.roundId}-${event.questionId}`;
        this.state = event;
      });
  }

}

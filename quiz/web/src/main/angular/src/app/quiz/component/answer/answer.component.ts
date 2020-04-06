import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {StateChanged} from '../../model/state-changed';

@Component({
  selector: 'app-answer',
  templateUrl: './answer.component.html',
  styleUrls: ['./answer.component.css']
})
export class AnswerComponent implements OnInit, OnChanges {

  answer: string;
  disabled: boolean = true;

  @Input()
  state: StateChanged;

  constructor() {
  }

  ngOnInit() {
  }

  send(
    $event: MouseEvent
  ) {

  }

  ngOnChanges(
    changes: SimpleChanges
  ): void {
    console.log(changes);
  }
  
}

import {Component, Input, OnInit} from '@angular/core';
import {Empire} from '../../model/empire';

@Component({
  selector: 'app-empire',
  templateUrl: './empire.component.html',
  styleUrls: ['./empire.component.css']
})
export class EmpireComponent implements OnInit {

  @Input('empire')
  empire: Empire;

  constructor() {
  }

  ngOnInit() {
  }

}

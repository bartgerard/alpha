import {Component, OnInit} from '@angular/core';
import {Subscribable} from '../../../common/service/subscribable.service';
import {Observable} from 'rxjs';
import {Empire} from '../../model/empire';
import {EmpireService} from '../../service/empire.service';

@Component({
  selector: 'app-empires',
  templateUrl: './empires.component.html',
  styleUrls: ['./empires.component.css']
})
export class EmpiresComponent extends Subscribable implements OnInit {

  empires$: Observable<Empire[]>;

  constructor(
    private empireService: EmpireService
  ) {
    super();
  }

  ngOnInit() {
    this.empires$ = this.empireService.empires();
  }

}

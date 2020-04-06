import {Component, OnInit} from '@angular/core';
import {Subscribable} from '../../service/subscribable';
import {PingService} from '../../service/ping.service';

@Component({
  selector: 'app-ping',
  templateUrl: './ping.component.html',
  styleUrls: ['./ping.component.css']
})
export class PingComponent extends Subscribable implements OnInit {

  pong: string = null;

  constructor(
    public pingService: PingService
  ) {
    super();
  }

  ngOnInit() {
    this.subs.sink = this.pingService.ping()
      .subscribe(pong => this.pong = pong);
  }

}

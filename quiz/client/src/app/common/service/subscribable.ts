import {SubSink} from 'subsink';
import {OnDestroy} from '@angular/core';

export abstract class Subscribable implements OnDestroy {

  protected constructor(
    public subscriptions = new SubSink()
  ) {
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }

}

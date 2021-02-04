import {SubSink} from 'subsink';
import {OnDestroy} from '@angular/core';

export abstract class Subscribable implements OnDestroy {

    protected constructor(
        protected subs = new SubSink()
    ) {
    }

    ngOnDestroy(): void {
        this.subs.unsubscribe();
    }

}

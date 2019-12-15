import {Injectable, NgZone} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SseService {

  constructor(
    private _zone: NgZone
  ) {
  }

  static getEventSource(
    url: string
  ): EventSource {
    return new EventSource(url);
  }

  getServerSentEvent<T>(
    url: string
  ): Observable<T> {
    return new Observable<T>(
      observer => {
        const eventSource = SseService.getEventSource(url);

        eventSource.onmessage = event => this._zone.run(() => observer.next(event.data));
        eventSource.onerror = error => this._zone.run(() => observer.error(error));

        return () => eventSource.close();
      }
    );
  }

}

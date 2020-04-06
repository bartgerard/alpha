import {Injectable} from '@angular/core';
import {SseService} from '../../common/service/sse.service';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';
import {StateChanged} from '../model/state-changed';

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  constructor(
    private _sseService: SseService
  ) {
  }

  events(): Observable<StateChanged> {
    return this._sseService.getServerSentEvent<StateChanged>(environment.serverUrl + '/quiz/stream-sse-emitter');
  }

}

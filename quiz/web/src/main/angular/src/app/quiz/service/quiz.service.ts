import {Injectable} from '@angular/core';
import {SseService} from '../../common/service/sse.service';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuizService {

  constructor(
    private _sseService: SseService
  ) {
  }

  events(): Observable<string> {
    return this._sseService.getServerSentEvent<string>(environment.serverUrl + '/quiz/stream-sse');
  }

}

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PingService {

  constructor(
    private _http: HttpClient
  ) {
  }

  ping(): Observable<string> {
    return this._http.get<string>(
      environment.serverUrl + '/ping',
      {responseType: 'text' as 'json'}
      );
  }

}

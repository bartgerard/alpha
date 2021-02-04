import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Empire} from '../model/empire';

@Injectable({
  providedIn: 'root'
})
export class EmpireService {

  constructor(
    private _http: HttpClient
  ) {
  }

  empires(): Observable<Empire[]> {
    return this._http.get<Empire[]>(
      environment.serverUrl + '/empires'
    );
  }

}

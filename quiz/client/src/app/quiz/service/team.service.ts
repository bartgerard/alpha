import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Team} from '../model/team';

@Injectable({
  providedIn: 'root'
})
export class TeamService {

  constructor(
    private http: HttpClient
  ) {
  }

  teams(): Observable<Team[]> {
    return this.http.get<Team[]>(
      environment.serverUrl + '/teams'
    );
  }

}

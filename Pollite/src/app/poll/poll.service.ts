import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Poll} from './poll';

@Injectable({
  providedIn: 'root'
})
export class PollService {
  constructor(private http: HttpClient) { }

  testGet() {

    // this line works
    this.http.get('http://localhost:3000/poll/0xHASHCODE').subscribe((data) => console.log(data));
  }

  getPoll(uuid: string): Observable<Poll> {
    return this.http.get<Poll>(environment.serverEndpoint + '/poll/' + uuid);
  }

  post(json): Observable<Object> {
    return this.http.post(environment.serverEndpoint + '/new/poll', json);
  }
}

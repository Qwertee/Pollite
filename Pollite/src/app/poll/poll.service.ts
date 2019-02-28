import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {Poll, VoteResponse} from './poll';

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

  /**
   * Submits a vote attempt to the server. Returns an observable for VoteResponse which contains the
   * poll whether it was modified or not and a status showing whether the vote was successful or not.
   * @param optionUuid uuid of the option to vote for.
   * @param fingerprint calculated fingerprint of the browser trying to submit the vote.
   */
  submitVote(optionUuid: string, fingerprint: string): Observable<VoteResponse> {
    return this.http.post<VoteResponse>(environment.serverEndpoint + '/vote',
      {'optionUuid': optionUuid, 'fingerprint': fingerprint});
  }
}

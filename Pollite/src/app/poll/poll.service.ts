import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PollService {
  constructor(private http: HttpClient) { }

  testGet() {

    // this line works
    this.http.get('http://localhost:3000/poll/0xHASHCODE').subscribe((data) => console.log(data));
  }

  post(json): Observable<Object> {
    return this.http.post('http://localhost:3000/new/poll', json);
  }
}

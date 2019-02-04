import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PollService {
  constructor(private http: HttpClient) { }

  testGet() {

    // this line works
    this.http.get('http://localhost:3000/poll/0xHASHCODE').subscribe((data) => console.log(data));
  }

  testService() {
    console.log('the service works!');
  }
}

import { Component, OnInit } from '@angular/core';
import {Option, Poll} from '../poll';
import {PollService} from '../poll.service';
import {ActivatedRoute} from '@angular/router';

import * as Fingerprint2 from 'fingerprintjs2';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
  poll: Poll;
  private murmur: string;

  constructor(private pollService: PollService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.pollService.getPoll(this.route.snapshot.params.uuid).subscribe(res => {
      this.poll = res;
      console.log(this.poll.options);
    });

    setTimeout(() => {
      Fingerprint2.get((components) => {
        console.log(components);

        // taken from https://fingerprintjs.com/ src
        this.murmur = Fingerprint2.x64hash128(components.map(function(pair) {
          return pair.value;
        }).join(), 31);

        console.log('murmur: ' + this.murmur);
      });
    }, 500);
  }

  submitVote(option: Option) {
    console.log('submitting vote for: ', option);
  }
}

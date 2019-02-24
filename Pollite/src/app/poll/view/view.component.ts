import { Component, OnInit } from '@angular/core';
import {Poll} from '../poll';
import {PollService} from '../poll.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
  poll: Poll;

  constructor(private pollService: PollService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.pollService.getPoll(this.route.snapshot.params.uuid).subscribe(res => {
      this.poll = res;
    });
  }

}

import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, Validators} from '@angular/forms';
import {PollService} from '../poll.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-poll-new',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.css']
})
export class NewComponent implements OnInit {
  pollForm = this.fb.group({
    prompt: ['', Validators.required],
    options: this.fb.array([
      this.fb.control('')
    ], Validators.required)
  });

  addOption() {
    this.options.push(this.fb.control(''));
  }

  get options() {
    return this.pollForm.get('options') as FormArray;
  }

  onSubmit() {
    this.pollService.post(this.pollForm.value).subscribe(r => this.router.navigate(['/poll', r['uuid']]));
  }

  constructor(private fb: FormBuilder, private pollService: PollService, private router: Router) { }

  ngOnInit() {
  }

  removeOption(i: number) {
    this.options.removeAt(i);
  }
}

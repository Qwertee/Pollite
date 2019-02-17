import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, Validators} from '@angular/forms';
import {PollService} from '../poll.service';

@Component({
  selector: 'app-poll-new',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.css']
})
export class NewComponent implements OnInit {
  pollForm = this.fb.group({
    pollName: ['', Validators.required],
    pollOptions: this.fb.array([
      this.fb.control('')
    ], Validators.required)
  });

  addOption() {
    this.pollOptions.push(this.fb.control(''));
  }

  get pollOptions() {
    return this.pollForm.get('pollOptions') as FormArray;
  }

  onSubmit() {
    console.warn(this.pollForm.value);
    this.pollService.post(this.pollForm.value).subscribe();
  }

  constructor(private fb: FormBuilder, private pollService: PollService) { }

  ngOnInit() {
  }

  removeOption(i: number) {
    this.pollOptions.removeAt(i);
  }
}

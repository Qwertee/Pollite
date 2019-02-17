import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, Validators} from '@angular/forms';

@Component({
  selector: 'new-poll-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {
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
  }

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
  }

  removeOption(i: number) {
    this.pollOptions.removeAt(i);
  }
}

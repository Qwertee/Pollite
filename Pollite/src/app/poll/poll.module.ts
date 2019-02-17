import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewComponent } from './new/new.component';
import {ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [NewComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [
    NewComponent
  ]
})
export class PollModule { }

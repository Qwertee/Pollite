import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NewComponent} from "./new.component";
import {NewPollFormModule} from "./new-poll-form/new-poll-form.module";

@NgModule({
  declarations: [NewComponent],
  imports: [
    CommonModule,
    NewPollFormModule
  ],
  exports: [
    NewComponent
  ]
})
export class NewModule { }

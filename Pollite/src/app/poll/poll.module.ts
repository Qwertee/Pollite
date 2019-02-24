import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewComponent } from './new/new.component';
import {ReactiveFormsModule} from '@angular/forms';
import { ViewComponent } from './view/view.component';

@NgModule({
  declarations: [NewComponent, ViewComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  exports: [
    NewComponent
  ]
})
export class PollModule { }

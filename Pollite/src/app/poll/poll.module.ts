import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewComponent } from './new/new.component';
import {NewModule} from "./new/new.module";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    NewModule
  ]
})
export class PollModule { }

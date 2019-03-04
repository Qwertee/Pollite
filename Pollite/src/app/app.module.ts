import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import {RouterModule, Routes} from '@angular/router';
import {PollModule} from './poll/poll.module';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import {FormsModule} from '@angular/forms';
import { NewComponent } from './poll/new/new.component';
import {ViewComponent} from './poll/view/view.component';
import { environment } from '../environments/environment';

const appRoutes: Routes = [
  {path: 'new', component: NewComponent},
  {path: 'poll/:uuid', component: ViewComponent},
  {path: '', redirectTo: '/new', pathMatch: 'full'}, // by default go to the new poll page (nothing interesting for home page so far)

  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
  ],
  imports: [
    RouterModule.forRoot(appRoutes, {enableTracing: !environment.production}),
    PollModule,
    BrowserModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

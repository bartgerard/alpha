import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import { EmpireComponent } from './mega-empires/component/empire/empire.component';
import { EmpiresComponent } from './mega-empires/component/empires/empires.component';

@NgModule({
  declarations: [
    AppComponent,
    EmpireComponent,
    EmpiresComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}

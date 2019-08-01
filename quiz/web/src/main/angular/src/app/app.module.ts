import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {registerLocaleData} from '@angular/common';
import localeNl from '@angular/common/locales/nl';
import {PingComponent} from './common/component/ping/ping.component'
import {HttpClientModule} from '@angular/common/http';
import {QuizModule} from './quiz/quiz.module';

@NgModule({
  declarations: [
    AppComponent,
    PingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    QuizModule
  ],
  providers: [],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}

registerLocaleData(localeNl, 'nl')

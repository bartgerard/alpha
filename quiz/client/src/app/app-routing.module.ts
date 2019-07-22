import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PingComponent} from './common/component/ping/ping.component';
import {TeamsComponent} from './quiz/component/teams/teams.component';

const routes: Routes = [
  {path: '', component: PingComponent},
  {path: 'teams', component: TeamsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

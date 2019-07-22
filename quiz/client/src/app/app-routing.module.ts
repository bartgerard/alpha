import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PingComponent} from './common/component/ping/ping.component';

const routes: Routes = [
  {path: '', component: PingComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

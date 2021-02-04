import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmpiresComponent} from './mega-empires/component/empires/empires.component';

const routes: Routes = [
  {path: '', component: EmpiresComponent},
  {path: 'empires', component: EmpiresComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

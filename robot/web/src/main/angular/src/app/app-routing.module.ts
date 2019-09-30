import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RobotComponent} from './robot/component/robot/robot.component';
import {ControlComponent} from './robot/component/control/control.component';
import {DrawingComponent} from './robot/component/drawing/drawing.component';

const routes: Routes = [
  {path: '', component: ControlComponent},
  {path: 'robot', component: RobotComponent},
  {path: 'drawing', component: DrawingComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

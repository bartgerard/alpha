import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RobotComponent} from './component/robot/robot.component';
import { ControlComponent } from './component/control/control.component';
import { DrawingComponent } from './component/drawing/drawing.component';
import { StripezComponent } from './component/stripez/stripez.component';

@NgModule({
  declarations: [
    RobotComponent,
    ControlComponent,
    DrawingComponent,
    StripezComponent,
  ],
  exports: [
    RobotComponent,
    ControlComponent,
  ],
  imports: [
    CommonModule
  ]
})
export class RobotModule {
}

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RobotComponent} from './component/robot/robot.component';

@NgModule({
  declarations: [
    RobotComponent
  ],
  exports: [
    RobotComponent
  ],
  imports: [
    CommonModule
  ]
})
export class RobotModule {
}

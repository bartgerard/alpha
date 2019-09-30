import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Square} from '../../model/square';

@Component({
  selector: 'app-robot',
  templateUrl: './robot.component.html',
  styleUrls: ['./robot.component.css']
})
export class RobotComponent implements OnInit {

  // https://blog.angularindepth.com/how-to-get-started-with-canvas-animations-in-angular-2f797257e5b4

  @ViewChild('canvas', {static: true})
  canvas: ElementRef<HTMLCanvasElement>;

  private ctx: CanvasRenderingContext2D;

  constructor() {
  }

  ngOnInit() {
    this.ctx = this.canvas.nativeElement.getContext('2d');

    this.ctx.fillStyle = 'red';
    const square = new Square(this.ctx);
    square.draw(5, 1, 20);
  }

}

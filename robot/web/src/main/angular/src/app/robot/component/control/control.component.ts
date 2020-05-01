import {AfterViewInit, Component, ElementRef, Input, OnDestroy, ViewChild} from '@angular/core';
import {SubSink} from 'subsink';
import {fromEvent} from 'rxjs';
import {Point} from '../../model/point';
import {StripezComponent} from '../stripez/stripez.component';
import {pairwise, switchMap, takeUntil} from 'rxjs/operators';

@Component({
  selector: 'app-control',
  templateUrl: './control.component.html',
  styleUrls: ['./control.component.css']
})
export class ControlComponent implements AfterViewInit, OnDestroy {

  private subs = new SubSink();

  // https://developer.mozilla.org/en-US/docs/Web/API/Canvas_API/Tutorial/Drawing_shapes

  @ViewChild('canvas', {static: true})
  canvas: ElementRef<HTMLCanvasElement>;

  @Input() public width = 500;
  @Input() public height = 500;

  @Input() public dotSize = 5;

  private ctx: CanvasRenderingContext2D;

  private points: Point[] = [];


  constructor() {
  }

  ngAfterViewInit(): void {
    this.ctx = this.canvas.nativeElement.getContext('2d');

    this.captureEvents(this.canvas.nativeElement);
  }

  captureEvents(
    canvas: HTMLCanvasElement
  ) {
    /*
    this.subs.sink = fromEvent(canvas, 'mouseup')
      .subscribe((event: MouseEvent) => {
        const rect = canvas.getBoundingClientRect();

        const p = new Point(event.clientX - rect.left, event.clientY - rect.top);
        this.points.push(p);

        this.render();
      });
     */

    this.subs.sink = fromEvent(canvas, 'mousedown')
      .pipe(
        switchMap(e => fromEvent(canvas, 'mousemove')
          .pipe(
            takeUntil(fromEvent(canvas, 'mouseup')),
            takeUntil(fromEvent(canvas, 'mouseleave')),
            pairwise()
          )
        ))
      .subscribe(([e1, e2]: [MouseEvent, MouseEvent]) => {
        const rect = canvas.getBoundingClientRect();

        const p1 = new Point(e1.clientX - rect.left, e1.clientY - rect.top);
        const p2 = new Point(e2.clientX - rect.left, e2.clientY - rect.top);

        this.render();
        this.drawPoint(p2);
      });
  }

  private render() {
    this.ctx.clearRect(0, 0, this.canvas.nativeElement.width, this.canvas.nativeElement.height);
    StripezComponent.drawLines(this.ctx, this.points);
    this.points.forEach(p => this.drawPoint(p))
  }

  drawPoint(
    p: Point
  ) {
    this.ctx.fillRect(p.x - this.dotSize / 2, p.y - this.dotSize / 2, this.dotSize, this.dotSize);
  }

  drawOnCanvas(
    prevPos: { x: number; y: number },
    currentPos: { x: number; y: number }
  ) {
    // incase the context is not set
    if (!this.ctx) {
      return;
    }

    // start our drawing path
    this.ctx.beginPath();

    // we're drawing lines so we need a previous position
    if (prevPos) {
      // sets the start point
      this.ctx.moveTo(prevPos.x, prevPos.y); // from
      // draws a line from the start pos until the current position
      this.ctx.lineTo(currentPos.x, currentPos.y);

      // strokes the current path with the styles we set earlier
      this.ctx.stroke();
    }
  }

  ngOnDestroy(): void {
    this.subs.unsubscribe();
  }

}

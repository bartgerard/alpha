import {AfterViewInit, Component, ElementRef, Input, OnDestroy, ViewChild} from '@angular/core';
import {SubSink} from 'subsink';
import {fromEvent} from 'rxjs';
import {pairwise, switchMap, takeUntil} from 'rxjs/operators';
import {Line} from '../../model/line';
import {Point} from '../../model/point';

@Component({
  selector: 'app-stripez',
  templateUrl: './stripez.component.html',
  styleUrls: ['./stripez.component.css']
})
export class StripezComponent implements AfterViewInit, OnDestroy {

  private subs = new SubSink();

  // https://gist.github.com/anupkrbid/6447d97df6be6761d394f18895bc680d
  // https://teropa.info/blog/2016/12/12/graphics-in-angular-2.html

  @ViewChild('canvas', {static: true})
  canvas: ElementRef<HTMLCanvasElement>;

  @Input() public width = 400;
  @Input() public height = 400;

  private ctx: CanvasRenderingContext2D;

  private lines: Line[] = [
    new Line(new Point(), new Point(10, 10))
  ];

  private points: Point[] = [
    new Point(),
    new Point(10, 10),
    new Point(20, 10),
    new Point(30, 20),
  ];

  constructor() {
  }

  ngAfterViewInit(): void {
    this.ctx = this.canvas.nativeElement.getContext('2d');

    this.captureEvents(this.canvas.nativeElement);
    this.ctx.scale(5, 5);
    //StripezComponent.render(this.ctx, this.lines);
    StripezComponent.drawLines(this.ctx, this.points);
  }

  captureEvents(
    canvas: HTMLCanvasElement
  ) {
    this.subs.sink = fromEvent(canvas, 'mousedown')
      .pipe(
        switchMap(e => {
          // after a mouse down, we'll record all mouse moves
          return fromEvent(canvas, 'mousemove')
            .pipe(
              // we'll stop (and unsubscribe) once the user releases the mouse
              // this will trigger a 'mouseup' event
              takeUntil(fromEvent(canvas, 'mouseup')),
              // we'll also stop (and unsubscribe) once the mouse leaves the canvas (mouseleave event)
              takeUntil(fromEvent(canvas, 'mouseleave')),
              // pairwise lets us get the previous value to draw a line from
              // the previous point to the current point
              pairwise()
            );
        })
      )
      .subscribe((res: [MouseEvent, MouseEvent]) => {
        const rect = canvas.getBoundingClientRect();

        // previous and current position with the offset
        const prevPos = {
          x: res[0].clientX - rect.left,
          y: res[0].clientY - rect.top
        };

        const currentPos = {
          x: res[1].clientX - rect.left,
          y: res[1].clientY - rect.top
        };

        // this method we'll implement soon to do the actual drawing
        this.drawOnCanvas(prevPos, currentPos);
      });

    this.subs.sink = fromEvent(canvas, 'mousewheel', {'passive': true})
      .subscribe((event: WheelEvent) => {
        console.log(event);
        //const zoom = 1 + (event.wheelDelta / 120) / 2;
        this.ctx.translate(-1, 0);
        //this.ctx.scale(2, 2);

      })
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

  static render(
    ctx: CanvasRenderingContext2D,
    lines: Line[]
  ) {
    lines.forEach(line => {
      StripezComponent.drawLine(ctx, line.p1, line.p2);
    })
  }

  static drawLines(
    ctx: CanvasRenderingContext2D,
    points: Point[]
  ) {
    //from(points)
    //  .pipe(
    //    //startWith(null),
    //    pairwise()
    //  )
    //  .subscribe(([p1, p2]: [Point, Point]) => {
    //    this.drawLine(ctx, p1, p2);
    //  });

    ctx.beginPath();
    points.forEach(p => ctx.lineTo(p.x, p.y));
    ctx.stroke();
  }

  static drawLine(
    ctx: CanvasRenderingContext2D,
    p1: Point,
    p2: Point
  ) {
    ctx.beginPath();
    ctx.moveTo(p1.x, p1.y);
    ctx.lineTo(p2.x, p2.y);
    ctx.stroke();
  }

}

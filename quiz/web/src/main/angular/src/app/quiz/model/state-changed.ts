export class StateChanged {

  constructor(
    public roundId: number,
    public questionId: number,
    public questionState: string
  ) {
  }

}

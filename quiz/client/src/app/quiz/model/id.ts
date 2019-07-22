export class Id<T> {

  constructor(
    public value: T
  ) {
  }

  public static of<T>(
    value: T
  ) {
    return new Id<T>(value);
  }

}

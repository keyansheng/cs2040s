/*
1.
  a.  class: blueprint
      object: instance
  b.  the main method belongs to the class itself, and not any particular object, and it can be called without creating any objects
  c.
*/
class Tut1 {
  public static int incorrect() {
    return Incorrect.incorrect();
  }
}

class Incorrect {
  private static int incorrect() {
    return 2040;
  }
}
/*
  d.
    i.    to specify functionality without requiring a specific implementation
    ii.   an IShape interface that specifies a getArea() method
          classes Circle and Square that implement IShape
          a method that works with anything that implements IShape
    iii.  yes
  e. 8, 8, 8, 7, 8, 7
  f. yes. this.
2.
  a.  O(n^3)
  b.  O(n^2 log n)
  c.  O(n^5)
  d.  O(2^(2n^2)) - wrong - O(2^(2n^2 + 4n))
3.
  a.  O(n)
  b.  O(n log n)
  c.  O(n)
  d.  O(log n)
  e.  O(n ^ (log n)) - wrong - depends on constant
4.  compare the index and value to determine if you're to the left or right of the missing element
5.
6.
*/

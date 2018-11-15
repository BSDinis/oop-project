package sth;

import java.io.IOException;
import java.io.Serializable;

/**
 * Student implementation.
 */
public class Student 
  extends Person 
  implements Serializable {

  Student(String n, String pN, int id) { super(n, pN, id); }

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811140053L;
  // FIXME

}

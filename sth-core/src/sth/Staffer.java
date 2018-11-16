package sth;

import java.io.Serializable;

/**
 * Staffer implementation.
 */
public class Staffer 
  extends Person 
  implements Serializable {

  Staffer(String n, String pN, int id, School s) { super(n, pN, id, s); }

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811151230L;
}

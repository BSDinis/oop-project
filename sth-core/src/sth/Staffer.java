package sth;

import java.io.Serializable;

/**
 * Staffer implementation.
 */
public class Staffer 
  extends Person 
  implements Serializable {

  Staffer(String n, String pN, int id) { super(n, pN, id); }

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811151230L;

}

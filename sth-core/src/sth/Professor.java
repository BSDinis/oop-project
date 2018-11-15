package sth;

import java.io.Serializable;

/**
 * Professor implementation.
 */
public class Professor 
  extends Person 
  implements Serializable {

  Professor(String n, String pN, int id) { super(n, pN, id); }

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811151230L;

}

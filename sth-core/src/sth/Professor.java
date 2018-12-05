package sth;

import java.io.Serializable;

/**
 * Professor implementation.
 */
public class Professor 
  extends PersonWithDisciplines 
  implements Serializable {

  Professor(String n, String pN, int id, School s) { super(n, pN, id, s); }
}

package sth;

import java.io.Serializable;

class Staffer 
  extends Person 
  implements Serializable {

  Staffer(String n, String pN, int id, School s) { super(n, pN, id, s); }
}

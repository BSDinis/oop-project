package sth;

import java.io.Serializable;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Professor implementation.
 */
public class Professor 
  extends Person 
  implements Serializable {

  List<Discipline> _disciplines;
  Professor(String n, String pN, int id) { super(n, pN, id); }

  void addDiscipline(Discipline d) { _disciplines.add(d); }
  void removeDiscipline(Discipline d) { _disciplines.remove(d); }
}

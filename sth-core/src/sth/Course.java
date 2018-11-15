package sth;

import java.io.IOException;
import java.io.Serializable;

import java.lang.IllegalArgumentException;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Discipline implementation.
 */
public class Course 
  implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811151044L;

  private String _name;
  private List<Student> _representatives;
  private Map<String, Discipline> _disciplines = new TreeMap<String, Discipline>();

  Course(String name, List<Student> reps, List<Discipline> disciplines) {
    _name = name;
    _representatives = reps;

    for (Discipline d : disciplines)
      _disciplines.put(d.name(), d);
  }
  
  public String name() { return _name; }
  public boolean hasDiscipline(String name) { return _disciplines.containsKey(name); }
  public Discipline getDiscipline(String name) { return _disciplines.get(name); } // FIXME: necessary?

  public String toString() { return "<<Course :: to implement>>"; }

}

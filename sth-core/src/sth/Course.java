package sth;

import java.io.IOException;
import java.io.Serializable;

import java.lang.IllegalArgumentException;

import java.util.Collection;
import java.util.List;
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

  private int _maxRepresentatives;
  private List<Student> _representatives;
  private Map<String, Discipline> _disciplines = new TreeMap<String, Discipline>();

  Course(String name, int maxRepresentatives, List<Student> reps, List<Discipline> disciplines) {
    if (reps.size() > maxRepresentatives) throw new IllegalArgumentException();
    _maxRepresentatives = maxRepresentatives;
    _name = name;
    _representatives = reps;

    for (Discipline d : disciplines)
      _disciplines.put(d.name(), d);
  }
  
  public String name() { return _name; }
  public boolean hasDiscipline(String name) { return _disciplines.containsKey(name); }
  public Discipline getDiscipline(String name) { return _disciplines.get(name); } 

  public boolean hasRepresentative(int id) { 
    for (Student s : _representatives)
      if (s.id() == id) return true;

    return false;
  }
  public Student getRepresentative(int id) {
    for (Student s : _representatives)
      if (s.id() == id) return s;

    return null;
  }

  public String toString() { return "<<Course :: to implement>>"; }

}

package sth;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import sth.exceptions.TooManyRepresentativesException;


/* methods are package on purpose */

public class Course 
  implements Serializable {

  private String _name;
  private int _maxRepresentatives;
  private Collection<Student> _representatives = new ArrayList<Student>();
  private Map<String, Discipline> _disciplines = new TreeMap<String, Discipline>();

  Course(String name, int maxRepresentatives) {
    _maxRepresentatives = maxRepresentatives;
    _name = name;
  }

  String name() { return _name; }

  Discipline addDiscipline(Discipline d) { _disciplines.put(d.name(), d); return d; } 

  boolean hasDiscipline(String name) { return _disciplines.containsKey(name); }

  Discipline getDiscipline(String name) { return _disciplines.get(name); } 

  Collection<Discipline> disciplines() { return _disciplines.values(); }

  boolean hasRepresentative(int id) { 
    for (Student s : _representatives)
      if (s.id() == id) return true;

    return false;
  }

  Student getRepresentative(int id) {
    for (Student s : _representatives)
      if (s.id() == id) return s;

    return null;
  }

  void electRepresentative(Student newRep) 
    throws TooManyRepresentativesException {
    if (_representatives.size() != _maxRepresentatives)
      _representatives.add(newRep);
    else
      throw new TooManyRepresentativesException();
  }
  
  void demoteRepresentative(Student oldRep) {
    _representatives.remove(oldRep);
  }

}

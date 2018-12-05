package sth;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import sth.exceptions.TooManyRepresentativesException;


/* methods are package on purpose */

public class Course 
  implements Serializable {

  private String _name;
  private int _maxRepresentatives;
  private Map<Integer, Student> _representatives = new TreeMap<>();
  private Map<String, Discipline> _disciplines = new TreeMap<>();

  Course(String name, int maxRepresentatives) {
    _maxRepresentatives = maxRepresentatives;
    _name = name;
  }

  String name() { return _name; }

  Discipline addDiscipline(Discipline d) { _disciplines.put(d.name(), d); return d; } 

  boolean hasDiscipline(String name) { return _disciplines.containsKey(name); }

  Discipline getDiscipline(String name) { return _disciplines.get(name); } 

  Collection<Discipline> disciplines() { return _disciplines.values(); }

  boolean hasRepresentative(int id) { return _representatives.containsKey(id); }

  Student getRepresentative(int id) { return _representatives.get(id); }

  void electRepresentative(Student newRep) 
    throws TooManyRepresentativesException {
    if (_representatives.size() != _maxRepresentatives)
      _representatives.put(newRep.id(), newRep);
    else
      throw new TooManyRepresentativesException();
  }
  
  void demoteRepresentative(Student oldRep) {
    _representatives.remove(oldRep.id());
  }

}

package sth;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import sth.exceptions.TooManyRepresentativesException;
import sth.exceptions.DisciplineNotFoundException;
import sth.exceptions.ProjectNotFoundException;
import sth.exceptions.SurveyNotFoundException;


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

  Discipline getDiscipline(String name) throws DisciplineNotFoundException { 
    if (!hasDiscipline(name)) throw new DisciplineNotFoundException(name);  
    return _disciplines.get(name); 
  } 

  Project getProject(String disciplineName, String projectName) 
      throws DisciplineNotFoundException, ProjectNotFoundException { 
    Discipline d = getDiscipline(disciplineName);
    return d.getProject(projectName); 
  } 

  Survey getSurvey(String disciplineName, String projectName) 
      throws DisciplineNotFoundException, ProjectNotFoundException, SurveyNotFoundException { 
    Project p = getProject(disciplineName, projectName);
    return p.getSurvey();
  } 


  Collection<Discipline> getDisciplines() { return _disciplines.values(); }

  boolean hasRepresentative(int id) { return _representatives.containsKey(id); }

  Student getRepresentative(int id) { return _representatives.get(id); }

  Collection<Student> getRepresentatives() { return _representatives.values(); }

  void electRepresentative(Student newRep) 
    throws TooManyRepresentativesException {
    if (_representatives.size() != _maxRepresentatives)
      _representatives.put(newRep.id(), newRep);
    else
      throw new TooManyRepresentativesException(name());
  }
  
  void demoteRepresentative(Student oldRep) {
    _representatives.remove(oldRep.id());
  }

}

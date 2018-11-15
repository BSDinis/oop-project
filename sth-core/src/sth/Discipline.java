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
public class Discipline 
  implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201811151044L;

  private String _name;
  private int _capacity;
  private List<Student> _students;
  private List<Professor> _professors;
  private Map<String, Project> _projects = new TreeMap<String, Project>();

  Discipline(String name, int cap, List<Student> students, List<Professor> profs) {
    if (students.size() > cap) throw new IllegalArgumentException();
    _name = name;
    _capacity = cap;
    _students = students;
    _professors = profs;
  }
  
  public String name() { return _name; }
  public void addProject(String name) { _projects.put(name, new Project(name)); }
  public boolean hasProject(String name) { return _projects.containsKey(name); }
  public Project getProject(String name) { return _projects.get(name); }
  public Collection<Student> getStudents() { return _students; }

  public String toString() { return "<<Discipline :: to implement>>"; }

}
